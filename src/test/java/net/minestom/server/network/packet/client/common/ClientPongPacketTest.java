package net.minestom.server.network.packet.client.common;

import net.minestom.server.network.NetworkBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientPongPacketTest {

    @Test
    void testClientPongPacket() {
         ClientPongPacket clientPongPacket = new ClientPongPacket(0);
         NetworkBuffer networkBuffer = new NetworkBuffer();
         clientPongPacket.write(networkBuffer);
         var packet = new ClientPongPacket(networkBuffer);
         assertEquals(0, packet.id());
    }

}
