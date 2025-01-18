package net.minestom.server.network.player;

import net.minestom.server.network.packet.server.SendablePacket;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ConfigurationTask {

    void start(@NotNull Consumer<SendablePacket> task);

    ConfigurationTask.Type type();

    record Type(String id) {
        @Override
        public String toString() {
            return this.id;
        }
    }
}
