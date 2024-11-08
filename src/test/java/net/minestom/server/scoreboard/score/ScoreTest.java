package net.minestom.server.scoreboard.score;

import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testScoreWithOnlyAValue() {
        Score score = Score.of(10);
        assertEquals(10, score.value());
        assertNull(score.displayName());
        assertNull(score.numberFormat());
    }

    @Test
    void testScoreWithValueAndDisplayName() {
        Score score = Score.of(10, Component.text("Test"));
        assertEquals(10, score.value());
        assertEquals(Component.text("Test"), score.displayName());
        assertNull(score.numberFormat());
    }

    @Test
    void testScoreContentUpdate() {
        Score score = Score.of(120);
        assertEquals(120, score.value());
        assertNull(score.displayName());
        assertNull(score.numberFormat());

        score.updateValue(0);
        score.updateDisplay(Component.text("DisplayName"));

        assertNotEquals(120, score.value());
        assertEquals(0, score.value());
        assertEquals(Component.text("DisplayName"), score.displayName());
        assertNull(score.numberFormat());
    }
}
