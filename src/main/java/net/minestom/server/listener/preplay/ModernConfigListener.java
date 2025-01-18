package net.minestom.server.listener.preplay;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
import net.minestom.server.network.player.tasks.JoinWorldTask;
import net.minestom.server.network.player.tasks.SyncRegistriesTask;
import org.jetbrains.annotations.NotNull;

public final class ModernConfigListener {

    public static void selectKnownPacks(@NotNull ClientSelectKnownPacksPacket packet, @NotNull Player player) {
        System.out.println("Select known packs");
        SyncRegistriesTask synchronizeRegistriesTask = player.getPlayerConnection().getSynchronizeRegistriesTask();
        synchronizeRegistriesTask.handleResponse(packet.entries(), player.getPlayerConnection()::sendPacket);
        player.getPlayerConnection().finishCurrentTask(SyncRegistriesTask.TYPE);
    }

    public static void finishConfigListener(@NotNull ClientFinishConfigurationPacket packet, @NotNull Player player) {
        player.getPlayerConnection().finishCurrentTask(JoinWorldTask.TYPE);
        MinecraftServer.getConnectionManager().transitionConfigToPlay(player);
    }

}
