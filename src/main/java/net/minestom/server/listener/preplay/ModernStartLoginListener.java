package net.minestom.server.listener.preplay;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.auth.AuthEvent;
import net.minestom.server.event.auth.BungeeCordAuth;
import net.minestom.server.event.auth.BungeeGuardAuth;
import net.minestom.server.event.auth.MojangAuth;
import net.minestom.server.event.auth.OfflineModeAuth;
import net.minestom.server.event.auth.VelocityAuth;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
import net.minestom.server.network.packet.server.login.EncryptionRequestPacket;
import net.minestom.server.network.packet.server.login.LoginDisconnectPacket;
import net.minestom.server.network.packet.server.login.LoginPluginRequestPacket;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static net.minestom.server.network.NetworkBuffer.BYTE;

public final class ModernStartLoginListener {

    private static final Component ALREADY_CONNECTED = MiniMessage.miniMessage().deserialize(System.getProperty("already_connected_during_login", "<red>You are already on this server!"));
    private static final Component ERROR_DURING_LOGIN = MiniMessage.miniMessage().deserialize(System.getProperty("error_during_login", "<red>An error occurred during login!"));
    private static final Component INVALID_PROXY_RESPONSE = MiniMessage.miniMessage().deserialize(System.getProperty("invalid_proxy_response", "<red>Invalid proxy response!"));

    private ModernStartLoginListener() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static void modernLoginStartListener(@NotNull ClientLoginStartPacket packet, @NotNull PlayerConnection connection) {
        if (connection instanceof PlayerSocketConnection socketConnection) {
            socketConnection.UNSAFE_setLoginUsername(packet.username());
        }
        final var event = AuthEvent.of(connection).<AuthEvent>call();
        var auth = event.getAuth();

        switch (auth) {
            case OfflineModeAuth offlineModeAuth -> {
                UUID playerUuid = MinecraftServer.getConnectionManager().getPlayerConnectionUuid(connection, packet.username());
                MinecraftServer.getConnectionManager().createPlayer(connection, playerUuid, packet.username());
                connection.setAuth(offlineModeAuth);
            }

            // Mojang auth
            case MojangAuth ma when connection instanceof PlayerSocketConnection socketConnection -> {
                if (MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(packet.username()) != null) {
                    connection.sendPacket(new LoginDisconnectPacket(ALREADY_CONNECTED));
                    connection.disconnect();
                    return;
                }
                final byte[] publicKey = ma.keyPair().getPublic().getEncoded();
                byte[] nonce = new byte[4];
                ThreadLocalRandom.current().nextBytes(nonce);
                socketConnection.setNonce(nonce);
                socketConnection.sendPacket(new EncryptionRequestPacket("", publicKey, nonce, true));
                socketConnection.setAuth(ma);
            }
            // Velocity auth
            case VelocityAuth velocityAuth when connection instanceof PlayerSocketConnection socketConnection -> {
                socketConnection.setVelocityLoginMessageId(ThreadLocalRandom.current().nextInt());
                NetworkBuffer buffer = new NetworkBuffer();
                buffer.write(BYTE, VelocityAuth.MAX_SUPPORTED_FORWARDING_VERSION);
                connection.sendPacket(new LoginPluginRequestPacket(socketConnection.getVelocityLoginMessageId(), VelocityAuth.PLAYER_INFO_CHANNEL, NetworkBuffer.makeArray(buffer)));
                socketConnection.setAuth(velocityAuth);
            }
            // BungeeGuard Auth
            case BungeeGuardAuth bungeeGuardAuth when connection instanceof PlayerSocketConnection socketConnection -> {
                GameProfile gameProfile = socketConnection.gameProfile();
                if (gameProfile == null) {
                    connection.disconnect();
                    return;
                }
                UUID playerUuid = gameProfile.uuid();
                MinecraftServer.getConnectionManager().createPlayer(connection, playerUuid, packet.username());
                socketConnection.setAuth(bungeeGuardAuth);
            }
            // BungeeCord Auth
            case BungeeCordAuth bungeeCordAuth when connection instanceof PlayerSocketConnection socketConnection -> {
                GameProfile gameProfile = socketConnection.gameProfile();
                if (gameProfile == null) {
                    connection.disconnect();
                    return;
                }
                UUID playerUuid = gameProfile.uuid();
                MinecraftServer.getConnectionManager().createPlayer(connection, playerUuid, packet.username());
                socketConnection.setAuth(bungeeCordAuth);
            }
            // Offline mode
            case null, default -> {
                LoginDisconnectPacket disconnectPacket = new LoginDisconnectPacket(ERROR_DURING_LOGIN);
                connection.sendPacket(disconnectPacket);
                connection.disconnect();
            }
        }

    }
}
