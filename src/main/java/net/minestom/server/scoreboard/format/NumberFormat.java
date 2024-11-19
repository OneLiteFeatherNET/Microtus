package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link NumberFormat} interface represents a number format which can be used to format a number in a scoreboard.
 * It provides a way to read and write number formats from/to a network buffer.
 * There are also some default defined number formats which are coming from the game Minecraft itself.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
@ApiStatus.Experimental
public sealed interface NumberFormat extends NetworkBuffer.Writer permits BlankFormat, StyledFormat, FixedFormat {

    NumberFormat BLANK = new BlankFormat();

    /**
     * A number format with no style
     */
    NumberFormat NO_STYLE = new StyledFormatImpl(Style.empty());

    /**
     * A number format with a red color
     */
    NumberFormat SIDEBAR_DEFAULT = new StyledFormatImpl(Style.empty().color(NamedTextColor.RED));

    /**
     * A number format with a yellow color
     */
    NumberFormat PLAYER_LIST_DEFAULT = new StyledFormatImpl(Style.empty().color(NamedTextColor.YELLOW));

    /**
     * Reads a number format from the network buffer.
     *
     * @param reader the network buffer to read from
     * @return the number format
     */
    default @NotNull NumberFormat read(@NotNull NetworkBuffer reader) {
        return NumberFormatNetworkAdapter.read(reader);
    }

    /**
     * Writes this number format to the network buffer.
     *
     * @param writer the network buffer to write to
     */
    @Override
    default void write(@NotNull NetworkBuffer writer) {
        NumberFormatNetworkAdapter.write(this, writer);
    }

    /**
     * Creates a scoreboard number format that applies a custom formatting to the score number.
     *
     * @param style the style to apply on the number
     * @return a styled number format
     */
    static @NotNull StyledFormat styled(final @NotNull Style style) {
        return new StyledFormatImpl(style);
    }

    /**
     * Creates a scoreboard number format that applies a custom formatting to the score number.
     *
     * @param builders the style builder to apply on the number
     * @return a styled number format
     */
    static @NotNull StyledFormat styled(final @NotNull StyleBuilderApplicable... builders) {
        return styled(Style.style(builders));
    }

    /**
     * Creates a scoreboard number format that replaces the score number with a chat component.
     *
     * @param component the component to replace the number with
     * @return a fixed number format
     */
    static @NotNull FixedFormat fixed(final @NotNull ComponentLike component) {
        return new FixedFormatImpl(component.asComponent());
    }

    /**
     * Gets the type of this number format.
     *
     * @return the type of this number format
     */
    @NotNull NumberFormat.FormatType type();

    /**
     * The enumeration representing the type of number format.
     *
     * @version 1.0.0
     * @see NumberFormat
     * @since 1.6.0
     */
    enum FormatType {
        BLANK,
        STYLED,
        FIXED;

        private static final FormatType[] VALUES = values();

        /**
         * Gets the format type from its ordinal.
         *
         * @param ordinal the ordinal
         * @return the format type, or null if the ordinal is invalid
         */
        static @Nullable NumberFormat.FormatType fromOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal >= VALUES.length) {
                return null;
            }
            return VALUES[ordinal];
        }
    }
}
