package net.minestom.server.network.packet.client.configuration;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.configuration.SelectKnownPacksPacket;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ClientSelectKnownPacksPacketTest {

    void testClientSelectKnownPacksPacket() {
        ClientSelectKnownPacksPacket clientSelectKnownPacksPacket = new ClientSelectKnownPacksPacket(
                List.of(
                        new SelectKnownPacksPacket.Entry("a", "a", "a")
                )
        );
        NetworkBuffer networkBuffer = new NetworkBuffer();
        clientSelectKnownPacksPacket.write(networkBuffer);
        var packet = new ClientSelectKnownPacksPacket(networkBuffer);

        assertArrayEquals(clientSelectKnownPacksPacket.entries().toArray(), packet.entries().toArray());
    }
}
