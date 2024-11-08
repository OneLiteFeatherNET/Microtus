package net.minestom.server.scoreboard.score;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DisplaySlotTest {

    private static @NotNull Stream<Arguments> indexRange() {
        return IntStream.range(0, DisplaySlot.values().length).mapToObj(Arguments::of);
    }

    private static @NotNull Stream<Arguments> teamIndexRange() {
        return IntStream.range(4, DisplaySlot.values().length).mapToObj(Arguments::of);
    }

    private static @NotNull Stream<Arguments> namedTextColorStream() {
        return NamedTextColor.NAMES.values().stream().map(Arguments::of);
    }

    @Test
    void testInvalidColorImplementationToGetDisplaySlot() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> DisplaySlot.byTextColor(TextColor.color(19, 200, 45)),
                "TextColor must be a NamedTextColor (Minecraft limitation)"
        );
    }

    @ParameterizedTest(name = "byID call for id: {0}")
    @MethodSource("indexRange")
    void testGetById(int id) {
        DisplaySlot displaySlot = DisplaySlot.byId(id);
        assertNotNull(displaySlot);
        assertEquals(id, displaySlot.getId());
    }

    @ParameterizedTest(name = "test team index for: {0}")
    @MethodSource("teamIndexRange")
    void testTeamColorStateSlot(int id) {
        DisplaySlot displaySlot = DisplaySlot.byId(id);
        assertNotNull(displaySlot);
        assertTeamColorState(displaySlot);
    }

    @ParameterizedTest
    @MethodSource("namedTextColorStream")
    void testNamedTextColorToDisplaySlot(NamedTextColor textColor) {
        DisplaySlot displaySlot = DisplaySlot.byTextColor(textColor);
        assertNotNull(displaySlot);
        assertTeamColorState(displaySlot);
    }

    /**
     * Asserts that the given {@link DisplaySlot} is a team color state slot.
     *
     * @param displaySlot the display slot to check
     */
    private void assertTeamColorState(@NotNull DisplaySlot displaySlot) {
        assertFalse(displaySlot.getSerializedName().contains("list"));
        assertNotEquals("sidebar".length(), displaySlot.getSerializedName().length());
        assertFalse(displaySlot.getSerializedName().contains("below_name"));
        assertTrue(displaySlot.getSerializedName().contains("team"));
    }
}
