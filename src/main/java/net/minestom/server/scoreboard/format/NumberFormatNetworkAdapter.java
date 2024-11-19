package net.minestom.server.scoreboard.format;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.utils.nbt.BinaryTagSerializer;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.scoreboard.format.NumberFormat.*;

/**
 * Internal utility to read and write {@link NumberFormat} from/to the network.
 * It helps to clean up the code base in the {@link NumberFormat} definition.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
@ApiStatus.Internal
public abstract class NumberFormatNetworkAdapter {

    /**
     * Reads a {@link NumberFormat} from the buffer.
     *
     * @param reader the buffer to read from
     * @return the number format
     */
    static @NotNull NumberFormat read(@NotNull NetworkBuffer reader) {
        FormatType type = FormatType.fromOrdinal(reader.read(NetworkBuffer.VAR_INT));
        Check.argCondition(type == null, "Unknown format type");
        return switch (type) {
            case BLANK -> BLANK;
            case STYLED -> {
                BinaryTag read = reader.read(NetworkBuffer.NBT);
                Style style;
                try {
                    style = BinaryTagSerializer.NBT_COMPONENT_STYLE.read(read);
                } catch (Exception e) {
                    style = Style.empty();
                }
                yield styled(style);
            }
            case FIXED -> {
                Component component = reader.read(NetworkBuffer.COMPONENT);
                yield fixed(component);
            }
        };
    }

    /**
     * Writes a {@link NumberFormat} to the buffer.
     *
     * @param numberFormat the number format to write
     * @param buffer       the buffer to write to
     */
    static void write(@NotNull NumberFormat numberFormat, @NotNull NetworkBuffer buffer) {
        buffer.write(NetworkBuffer.VAR_INT, numberFormat.type().ordinal());

        switch (numberFormat) {
            case StyledFormat styledFormat -> {
                BinaryTag write = BinaryTagSerializer.NBT_COMPONENT_STYLE.write(styledFormat.style());
                buffer.write(NetworkBuffer.NBT, write);
            }
            case FixedFormat fixedFormat -> buffer.write(NetworkBuffer.COMPONENT, fixedFormat.component());
            default -> buffer.write(NetworkBuffer.COMPONENT, Component.empty());
        }
    }

    private NumberFormatNetworkAdapter() {
        // Empty private constructor
    }
}
