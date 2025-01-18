package net.minestom.server.listener.preplay;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.auth.VelocityAuth;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import static net.minestom.server.network.NetworkBuffer.STRING;

public final class ModernPluginResponseListener {

    private static final ComponentLogger LOGGER = ComponentLogger.logger();

    public static void loginPluginResponseListener(@NotNull ClientLoginPluginResponsePacket packet, @NotNull PlayerConnection connection) {
        if (!(connection instanceof PlayerSocketConnection playerSocketConnection)) {
            connection.disconnect();
            return;
        }
        var auth = connection.getAuth();
        if (connection.getVelocityLoginMessageId() == -1 && auth instanceof VelocityAuth) {
            // Login Plugin Request
            LOGGER.error("Received Login Plugin Response with id -1");
            connection.disconnect();
            return;
        }

        var messageId = packet.messageId();
        if (connection.getVelocityLoginMessageId() != messageId) {
            // Invalid message id
            LOGGER.error("Received Login Plugin Response with invalid message id");
            connection.disconnect();
            return;
        }
        byte[] bytes = packet.data();
        if (bytes == null && auth instanceof VelocityAuth) {
            LOGGER.error("Received Login Plugin Response with null data");
            connection.disconnect();
            return;
        }
        if (bytes == null) {
            return;
        }
        var data = NetworkBuffer.of(ByteBuffer.wrap(bytes));
        if (auth instanceof VelocityAuth velocityAuth && !velocityAuth.checkIntegrity(data)) {
            LOGGER.error("Received Login Plugin Response with invalid data");
            connection.disconnect();
            return;
        }
        int version = data.read(NetworkBuffer.INT);
        if (version > VelocityAuth.MAX_SUPPORTED_FORWARDING_VERSION) {
            connection.disconnect();
            LOGGER.error("Unsupported forwarding version: " + version);
            return;
        }
        SocketAddress socketAddress = connection.getRemoteAddress();
        int port = 0;
        if (socketAddress instanceof InetSocketAddress address) {
            port = address.getPort();
        }

        final InetAddress address;
        try {
            address = InetAddress.getByName(data.read(STRING));
        } catch (UnknownHostException e) {
            MinecraftServer.getExceptionManager().handleException(e);
            connection.disconnect();
            return;
        }
        SocketAddress inetSocketAddress = new InetSocketAddress(address, port);
        var gameProfile = new GameProfile(data);

        playerSocketConnection.setRemoteAddress(inetSocketAddress);
        playerSocketConnection.UNSAFE_setProfile(gameProfile);
        MinecraftServer.getConnectionManager().createPlayer(playerSocketConnection, gameProfile.uuid(), gameProfile.name());
    }
}
