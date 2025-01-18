package net.minestom.server.network.player.tasks;

import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.packet.server.configuration.FinishConfigurationPacket;
import net.minestom.server.network.player.ConfigurationTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class JoinWorldTask implements ConfigurationTask {

    public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("join_world");

    @Override
    public void start(@NotNull Consumer<SendablePacket> task) {
        task.accept(FinishConfigurationPacket.INSTANCE);
    }

    @Override
    public ConfigurationTask.Type type() {
        return TYPE;
    }
}
