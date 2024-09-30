package net.minestom.server.network.packet.client.common;

import net.minestom.server.network.NetworkBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientPingRequestPacketTest {

    @Test
    void testClientPingRequestPacket() {
        NetworkBuffer networkBuffer = new NetworkBuffer();
        ClientPingRequestPacket requestPacket = new ClientPingRequestPacket(0L);

        requestPacket.write(networkBuffer);

        var packet = new ClientPingRequestPacket(networkBuffer);

        assertEquals(packet.number(), 0L);
    }

}
