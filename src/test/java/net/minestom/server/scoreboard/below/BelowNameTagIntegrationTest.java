package net.minestom.server.scoreboard.below;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.scoreboard.BelowNameTag;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

// I'm not a big fan of this construct but for now it's fine
@ExtendWith(MicrotusExtension.class)
class BelowNameTagIntegrationTest {

    @Test
    void testBelowNameTagViewerFlow(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        BelowNameTag belowNameTag = new BelowNameTag("belowName", Component.text("Hello"));

        assertNotNull(belowNameTag);
        assertTrue(belowNameTag.getViewers().isEmpty());

        assertTrue(belowNameTag.addViewer(player));
        assertFalse(belowNameTag.addViewer(player));
        assertFalse(belowNameTag.addViewer(player));

        assertTrue(belowNameTag.removeViewer(player));
        assertTrue(belowNameTag.addViewer(player));
        assertFalse(belowNameTag.getViewers().isEmpty());

        env.destroyInstance(instance, true);
    }
}
