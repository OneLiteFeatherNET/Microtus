package net.minestom.server.scoreboard.below;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.BelowNameTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BelowNameTagTest {

    @Test
    void testBelowNameTag() {
        BelowNameTag belowNameTag = new BelowNameTag("Test", Component.text("Test"));
        assertNotNull(belowNameTag);
        assertEquals("bnt-Test", belowNameTag.getObjectiveName());
    }

    @Test
    void testGetTitle() {
        BelowNameTag belowNameTag = new BelowNameTag("Test", Component.text("Test"));
        assertEquals(Component.empty(), belowNameTag.getTitle());
    }
}
