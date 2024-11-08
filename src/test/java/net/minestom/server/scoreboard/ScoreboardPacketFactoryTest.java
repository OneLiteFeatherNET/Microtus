package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.network.packet.server.play.DisplayScoreboardPacket;
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;
import org.junit.jupiter.api.Test;

import static net.minestom.server.scoreboard.ScoreboardPacketFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class ScoreboardPacketFactoryTest {

    @Test
    void testObjectiveCreationViaFactory() {
        Component text = Component.text("Test");
        ScoreboardObjectivePacket packet = getCreationObjectivePacket("dummy", text, ScoreboardObjectivePacket.Type.INTEGER);
        assertInstanceOf(ScoreboardObjectivePacket.class, packet);
        assertNotNull(packet);
        assertEquals("dummy", packet.objectiveName());
        assertNull(packet.numberFormat());
        assertEquals(text, packet.objectiveValue());
        assertEquals(ScoreboardObjectivePacket.Type.INTEGER, packet.type());
    }

    @Test
    void testObjectiveDestroyPacketViaFactory() {
        ScoreboardObjectivePacket packet = getDestructionObjectivePacket("dummy");
        assertInstanceOf(ScoreboardObjectivePacket.class, packet);
        assertNotNull(packet);
        assertEquals("dummy", packet.objectiveName());
    }

    @Test
    void testDisplayScoreboardPacketViaFactory() {
        DisplayScoreboardPacket packet = getDisplayScoreboardPacket("scoreDummy", (byte) 0);
        assertInstanceOf(DisplayScoreboardPacket.class, packet);
        assertNotNull(packet);
        assertEquals(0, packet.position());
        assertNotEquals("dummy", packet.scoreName());
    }
}
