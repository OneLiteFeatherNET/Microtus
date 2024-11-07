package net.minestom.server.scoreboard.tablist;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.TabList;
import org.junit.jupiter.api.Test;

import static net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket.*;
import static org.junit.jupiter.api.Assertions.*;

class TabListTest {

    @Test
    void testTabListCreation() {
        TabList tabList = new TabList("test", Type.INTEGER);
        assertNotNull(tabList);
        assertEquals(Type.INTEGER, tabList.getType());
        assertEquals(Component.empty(), tabList.getTitle());
        assertEquals("tl-test", tabList.getObjectiveName());
    }

    @Test
    void testTabListTypeChange() {
        TabList tabList = new TabList("test", Type.INTEGER);
        assertEquals(Type.INTEGER, tabList.getType());
        tabList.setType(Type.HEARTS);
        assertNotEquals(Type.INTEGER, tabList.getType());
        assertEquals(Type.HEARTS, tabList.getType());
    }
}
