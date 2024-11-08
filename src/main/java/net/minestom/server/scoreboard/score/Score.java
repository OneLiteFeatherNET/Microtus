package net.minestom.server.scoreboard.score;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.Sidebar.NumberFormat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Score} interface represents a score which can be shown in a scoreboard of the game Minecraft.
 * <p>
 * A score contains a value, which is the actual score or line (depends on the given context) and an optional display name as {@link Component}.
 * Additionally, a {@link NumberFormat} can be set to format the value.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
public interface Score {

    /**
     * Creates a new score with the specified value.
     *
     * @param value the value of the score
     * @return a new score
     */
    @Contract(pure = true, value = "_ -> new")
    static @NotNull Score of(int value) {
        return new ScoreboardScore(value, null, null);
    }

    /**
     * Creates a new score with the specified value and display name.
     *
     * @param value     the value of the score
     * @param component the display name of the score
     * @return a new score
     */
    @Contract(pure = true, value = "_, _ -> new")
    static @NotNull Score of(int value, @Nullable Component component) {
        return new ScoreboardScore(value, component, null);
    }

    /**
     * Creates a new score with the specified value, display name and number format.
     *
     * @param value        the value of the score
     * @param component    the display name of the score
     * @param numberFormat the number format of the score
     * @return a new score
     */
    @Contract(pure = true, value = "_, _, _ -> new")
    static @NotNull Score of(int value, @Nullable Component component, @Nullable NumberFormat numberFormat) {
        return new ScoreboardScore(value, component, numberFormat);
    }

    /**
     * Updates the value of the score.
     *
     * @param value the new value of the score
     */
    void updateValue(int value);

    /**
     * Updates the display name of the score.
     *
     * @param component the new display name of the score
     */
    void updateDisplay(@Nullable Component component);

    /**
     * Updates the number format of the score.
     *
     * @param numberFormat the new number format of the score
     */
    void updateNumberFormat(@Nullable NumberFormat numberFormat);

    /**
     * Gets the value of the score.
     *
     * @return the given value
     */
    int value();

    /**
     * Gets the display name of the score.
     *
     * @return the given display name
     */
    @Nullable Component displayName();

    /**
     * Gets the number format of the score.
     *
     * @return the given format
     */
    @Nullable NumberFormat numberFormat();
}
