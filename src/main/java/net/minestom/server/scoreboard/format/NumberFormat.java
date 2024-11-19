package net.minestom.server.scoreboard.format;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public sealed interface NumberFormat extends NetworkBuffer.Writer permits BlankFormat, StyledFormat, FixedFormat {

    /** A blank number format */
    NumberFormat BLANK = new BlankFormat();

    /** A number format with no style */
    NumberFormat NO_STYLE = new StyledFormatImpl(Style.empty());

    /** A number format with a red color */
    NumberFormat SIDEBAR_DEFAULT = new StyledFormatImpl(Style.empty().color(NamedTextColor.RED));

    /** A number format with a yellow color */
    NumberFormat PLAYER_LIST_DEFAULT = new StyledFormatImpl(Style.empty().color(NamedTextColor.YELLOW));

    int MAX_ID = 2;

    static @NotNull NumberFormat read(@NotNull NetworkBuffer reader) {
        int id = reader.read(NetworkBuffer.VAR_INT);
        Check.argCondition(id < 0, "The id cannot be negative");
        Check.argCondition(id > MAX_ID, "The id can't be higher than " + MAX_ID);
        return switch (id) {
            case 0 -> BLANK;
            case 1 -> styled(Style.empty());
            case 2 -> {
                Component component = reader.read(NetworkBuffer.COMPONENT);
                yield fixed(component);
            }
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

    @Override
    default void write(@NotNull NetworkBuffer writer) {
        writer.write(NetworkBuffer.VAR_INT, id());

        switch (this) {
            case StyledFormat styledFormat -> {
                // writer.write(NetworkBuffer.COMPONENT, styledFormat.style());
            }
            case FixedFormat fixedFormat -> writer.write(NetworkBuffer.COMPONENT, fixedFormat.component());
            default -> {
            }
        }
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
     * Gets the id of this number format.
     *
     * @return the id of this number format
     */
    int id();

}
