package net.minestom.server.network.packet.client.common;

import net.minestom.server.entity.Player;
import net.minestom.server.message.ChatMessageType;
import net.minestom.server.network.NetworkBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientSettingsPacketTest {

    @Test
    void testClientSettingsPacket() {
        ClientSettingsPacket clientSettingsPacket = new ClientSettingsPacket(
                "locale",
                (byte)0,
                ChatMessageType.FULL,
                true,
                (byte) 0,
                Player.MainHand.RIGHT,
                true,
                true);
        NetworkBuffer networkBuffer = new NetworkBuffer();
        clientSettingsPacket.write(networkBuffer);
        var packet = new ClientSettingsPacket(networkBuffer);
        assertEquals("locale", packet.locale());
        assertEquals(0, packet.viewDistance());
        assertEquals(ChatMessageType.FULL, packet.chatMessageType());
        assertTrue(packet.chatColors());
        assertEquals(0, packet.displayedSkinParts());
        assertEquals(Player.MainHand.RIGHT, packet.mainHand());
        assertTrue(packet.enableTextFiltering());
        assertTrue(packet.allowsListing());
    }
}
