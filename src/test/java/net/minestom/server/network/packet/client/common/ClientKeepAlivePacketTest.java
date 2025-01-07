package net.minestom.server.network.packet.client.common;

import net.minestom.server.network.NetworkBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientKeepAlivePacketTest {

    @Test
    void testClientKeepAlivePacket() {
        NetworkBuffer networkBuffer = new NetworkBuffer();
        ClientKeepAlivePacket clientKeepAlivePacket = new ClientKeepAlivePacket(0L);

        clientKeepAlivePacket.write(networkBuffer);

        var packet = new ClientKeepAlivePacket(networkBuffer);

        assertEquals(0L, packet.id());
    }
}
