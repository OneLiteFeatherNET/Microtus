package net.minestom.testing;

import net.kyori.adventure.translation.GlobalTranslator;
import net.minestom.server.ServerProcess;
import net.minestom.server.adventure.MinestomAdventure;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.ConnectionState;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@link TestConnectionImpl} is the implementation of the {@link TestConnection} interface.
 * It is used to connect players to the test environment.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
final class TestConnectionImpl implements TestConnection {

    private final ServerProcess process;
    private final PlayerConnectionImpl playerConnection = new PlayerConnectionImpl();
    private volatile Optional<PlayerProvider> playerProvider = Optional.of(TestPlayerImpl::new);

    private final List<IncomingCollector<ServerPacket>> incomingTrackers = new CopyOnWriteArrayList<>();

    /**
     * Creates a new instance of {@link TestConnectionImpl} with the given {@link Env}.
     *
     * @param env the environment to use
     */
    TestConnectionImpl(@NotNull Env env) {
        this.process = env.process();
    }

    /**
     * Sets the custom player provider for this test connection.
     *
     * @param provider the custom player provider
     */
    @Override
    public void setCustomPlayerProvider(@NotNull PlayerProvider provider) {
        this.playerProvider = Optional.ofNullable(provider);
    }

    /**
     * Connects a player to the server.
     *
     * @param instance the instance to spawn the player in
     * @param pos      the position to spawn the player at
     * @return a future that completes when the player is connected
     */
    @Override
    public @NotNull CompletableFuture<Player> connect(@NotNull Instance instance, @NotNull Pos pos) {
        // Use player provider to disable queued chunk sending
        process.connection().setPlayerProvider(playerProvider.orElse(TestPlayerImpl::new));

        playerConnection.setConnectionState(ConnectionState.LOGIN);
        var player = process.connection().createPlayer(playerConnection, UUID.randomUUID(), "RandName");
        player.eventNode().addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instance);
            event.getPlayer().setRespawnPoint(pos);
        });

        // Force the player through the entirety of the login process manually
        var configFuture = process.connection().doConfiguration(player, true);
        playerConnection.receiveKnownPacksResponse(List.of(SelectKnownPacksPacket.MINECRAFT_CORE));
        configFuture.join();

        process.connection().transitionConfigToPlay(player);
        process.connection().updateWaitingPlayers();
        return CompletableFuture.completedFuture(player);
    }

    /**
     * Tracks incoming packets of a specific type.
     *
     * @param type the packet type to track
     * @param <T>  the packet type
     * @return a collector for the tracked packets
     */
    @Override
    public @NotNull <T extends ServerPacket> Collector<T> trackIncoming(@NotNull Class<T> type) {
        var tracker = new IncomingCollector<>(type);
        this.incomingTrackers.add(IncomingCollector.class.cast(tracker));
        return tracker;
    }

    /**
     * Represents a player connection in the test environment.
     */
    final class PlayerConnectionImpl extends PlayerConnection {

        private boolean online = true;

        /**
         * Sends a packet to the player.
         *
         * @param packet the packet to send
         */
        @Override
        public void sendPacket(@NotNull SendablePacket packet) {
            final var serverPacket = this.extractPacket(packet);
            for (var tracker : incomingTrackers) {
                if (!tracker.type.isAssignableFrom(serverPacket.getClass())) continue;
                tracker.packets.add(serverPacket);
            }
        }

        /**
         * Extracts the server packet from the given packet.
         *
         * @param packet the packet to extract from
         * @return the extracted server packet
         */
        private ServerPacket extractPacket(final SendablePacket packet) {
            if (!(packet instanceof ServerPacket serverPacket))
                return SendablePacket.extractServerPacket(getConnectionState(), packet);

            final Player player = getPlayer();
            if (player == null) return serverPacket;

            if (MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION && serverPacket instanceof ServerPacket.ComponentHolding componentPacket) {
                serverPacket = componentPacket.copyWithOperator(component ->
                        GlobalTranslator.render(component, Objects.requireNonNullElseGet(player.getLocale(), MinestomAdventure::getDefaultLocale)));
            }

            return serverPacket;
        }

        /**
         * Gets the remote address of the connection.
         *
         * @return the remote address
         */
        @Override
        public @NotNull SocketAddress getRemoteAddress() {
            return new InetSocketAddress("localhost", 25565);
        }

        /**
         * Gets the status if the player is online or not.
         *
         * @return true if the player is online, false otherwise
         */
        @Override
        public boolean isOnline() {
            return online;
        }

        /**
         * Disconnects the player.
         */
        @Override
        public void disconnect() {
            online = false;
        }
    }

    /**
     * A collector for a specific type of packet.
     *
     * @param <T> the packet type
     */
    final class IncomingCollector<T extends ServerPacket> implements Collector<T> {

        private final Class<T> type;
        private final List<T> packets = new CopyOnWriteArrayList<>();

        /**
         * Creates a new incoming collector for the given packet type.
         *
         * @param type the packet type to collect
         */
        public IncomingCollector(@NotNull Class<T> type) {
            this.type = type;
        }

        /**
         * Collects a list of elements
         *
         * @return the list of elements
         */
        @Override
        public @NotNull List<T> collect() {
            incomingTrackers.remove(this);
            return List.copyOf(packets);
        }
    }
}
