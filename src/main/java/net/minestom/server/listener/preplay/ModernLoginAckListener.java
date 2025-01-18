package net.minestom.server.listener.preplay;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ModernLoginAckListener {
    public static void loginAckListener(@NotNull ClientLoginAcknowledgedPacket ignored, @NotNull PlayerConnection connection) {
        final Player player = Objects.requireNonNull(connection.getPlayer());
        MinecraftServer.getConnectionManager().modernDoConfiguration(player, true);
    }
}
