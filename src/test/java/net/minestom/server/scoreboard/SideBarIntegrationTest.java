package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MicrotusExtension.class)
class SideBarIntegrationTest {

    @Test
    void testViewerFlow(@NotNull Env env) {
        Sidebar sidebar = new Sidebar(Component.text("Test"));
        sidebar.createLine(new Sidebar.ScoreboardLine("1", Component.text("First Line"), 1));
        assertFalse(sidebar.getLines().isEmpty());
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        // The following block is a bit weird because it doesn't check if the player retrieves the give sidebar
        // For a future update, it would be better to check if the player actually gets the sidebar
        assertTrue(sidebar.addViewer(player));
        assertFalse(sidebar.addViewer(player));

        assertTrue(sidebar.removeViewer(player));
        assertFalse(sidebar.removeViewer(player));

        env.destroyInstance(instance, true);

    }
}
