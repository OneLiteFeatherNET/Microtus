package net.minestom.server.scoreboard.tablist;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket.Type;
import net.minestom.server.scoreboard.TabList;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

// I'm not a big fan of this construct but for now it's fine
@ExtendWith(MicrotusExtension.class)
class TabListIntegrationTest {

    @Test
    void testTabListViewerFlow(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        TabList tabList = new TabList("belowName", Type.HEARTS);

        assertNotEquals(Type.INTEGER, tabList.getType());

        assertNotNull(tabList);
        assertTrue(tabList.getViewers().isEmpty());

        assertTrue(tabList.addViewer(player));
        assertFalse(tabList.addViewer(player));
        assertFalse(tabList.addViewer(player));

        assertTrue(tabList.removeViewer(player));
        assertTrue(tabList.addViewer(player));
        assertFalse(tabList.getViewers().isEmpty());

        env.destroyInstance(instance, true);
    }
}
