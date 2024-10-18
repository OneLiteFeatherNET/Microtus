package net.minestom.server.network.packet.client.common;

import net.minestom.server.network.NetworkBuffer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientPluginMessagePacketTest {

    // TODO: Fix bug with empty byte array
    @Disabled
    @Test
    void testClientPluginMessagePacket() {
        NetworkBuffer networkBuffer = new NetworkBuffer();
        ClientPluginMessagePacket clientPluginMessagePacket = new ClientPluginMessagePacket("channel", new byte[0]);

        clientPluginMessagePacket.write(networkBuffer);

        var packet = new ClientPluginMessagePacket(networkBuffer);

        assertEquals("channel", packet.channel());
        assertArrayEquals(new byte[0], packet.data());
    }

    @Test
    void testClientPluginMessagePacketException() {
        StringBuilder channel = new StringBuilder();
        channel.append("a".repeat(257));
        assertThrows(IllegalArgumentException.class, () -> new ClientPluginMessagePacket(channel.toString(), new byte[0]));
    }
}
