package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link StyledFormatImpl} is the number format that applies a custom formatting to the score number.
 *
 * @param style the style to apply
 * @version 1.0.0
 * @since 1.6.0
 */
record StyledFormatImpl(@NotNull Style style) implements StyledFormat {

    @Override
    public void styleApply(Style.@NotNull Builder style) {
        style.merge(this.style);
    }

    @Override
    public @NotNull NumberFormat.FormatType type() {
        return FormatType.STYLED;
    }
}
