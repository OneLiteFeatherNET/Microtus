package net.minestom.server.network.packet.server.configuration;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

public record FinishConfigurationPacket() implements ServerPacket.Configuration {

    public static final FinishConfigurationPacket INSTANCE = new FinishConfigurationPacket();

    public FinishConfigurationPacket(@NotNull NetworkBuffer buffer) {
        this();
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
    }

    @Override
    public int configurationId() {
        return ServerPacketIdentifier.CONFIGURATION_FINISH_CONFIGURATION;
    }
}
