package net.minestom.server.network.player.tasks;

import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.packet.server.common.TagsPacket;
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;
import net.minestom.server.network.player.ConfigurationTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public final class SyncRegistriesTask implements ConfigurationTask {

    public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("synchronize_registries");

    private final List<SelectKnownPacksPacket.Entry> knownPacks;

    public SyncRegistriesTask(List<SelectKnownPacksPacket.Entry> knownPacks) {
        this.knownPacks = knownPacks;
    }

    @Override
    public void start(@NotNull Consumer<SendablePacket> task) {
        System.out.println("Select known packs");
        task.accept(new SelectKnownPacksPacket(this.knownPacks));
    }

    public void handleResponse(@NotNull List<SelectKnownPacksPacket.Entry> packs,@NotNull Consumer<SendablePacket> packetSender) {
        boolean excludeVanilla = packs.contains(SelectKnownPacksPacket.MINECRAFT_CORE);

        var serverProcess = MinecraftServer.process();
        packetSender.accept(serverProcess.registries().chatType().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().dimensionType().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().biome().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().damageType().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().trimMaterial().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().trimPattern().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().bannerPattern().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().wolfVariant().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().enchantment().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().paintingVariant().registryDataPacket(excludeVanilla));
        packetSender.accept(serverProcess.registries().jukeboxSong().registryDataPacket(excludeVanilla));

        packetSender.accept(TagsPacket.DEFAULT_TAGS);
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
