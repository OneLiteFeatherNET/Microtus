package net.minestom.server.scoreboard.format;

import org.junit.jupiter.api.Test;

import static net.minestom.server.scoreboard.format.NumberFormat.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberFormatTest {

    @Test
    void testDefaultConstants() {
        assertInstanceOf(BlankFormat.class, BLANK);
        assertInstanceOf(StyledFormat.class, NO_STYLE);
        assertInstanceOf(StyledFormat.class, PLAYER_LIST_DEFAULT);
        assertInstanceOf(StyledFormat.class, SIDEBAR_DEFAULT);
    }
}