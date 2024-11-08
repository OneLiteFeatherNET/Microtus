package net.minestom.server.scoreboard.score;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents each type of slot which a objective can have.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
public enum DisplaySlot {

    LIST(0, "list"),
    SIDEBAR(1, "sidebar"),
    BELOW_NAME(2, "below_name"),
    TEAM_BLACK(3, "sidebar.team.black"),
    TEAM_DARK_BLUE(4, "sidebar.team.dark_blue"),
    TEAM_DARK_GREEN(5, "sidebar.team.dark_green"),
    TEAM_DARK_AQUA(6, "sidebar.team.dark_aqua"),
    TEAM_DARK_RED(7, "sidebar.team.dark_red"),
    TEAM_DARK_PURPLE(8, "sidebar.team.dark_purple"),
    TEAM_GOLD(9, "sidebar.team.gold"),
    TEAM_GRAY(10, "sidebar.team.gray"),
    TEAM_DARK_GRAY(11, "sidebar.team.dark_gray"),
    TEAM_BLUE(12, "sidebar.team.blue"),
    TEAM_GREEN(13, "sidebar.team.green"),
    TEAM_AQUA(14, "sidebar.team.aqua"),
    TEAM_RED(15, "sidebar.team.red"),
    TEAM_LIGHT_PURPLE(16, "sidebar.team.light_purple"),
    TEAM_YELLOW(17, "sidebar.team.yellow"),
    TEAM_WHITE(18, "sidebar.team.white");

    private static final Map<Integer, DisplaySlot> BY_ID =
            Arrays.stream(values()).collect(Collectors.toMap(DisplaySlot::getId, Function.identity()));

    private final int id;
    private final String name;

    /**
     * Creates a new display slot.
     *
     * @param id   the id
     * @param name the name
     */
    DisplaySlot(int id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the display slot with the given id.
     *
     * @param id the id
     * @return the display slot with the given id, or null if none
     */
    public static @Nullable DisplaySlot byId(int id) {
        return BY_ID.get(id);
    }

    /**
     * Gets the id of this display slot.
     *
     * @return the id of this display slot
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this display slot.
     *
     * @return the name of this display slot
     */
    public @NotNull String getSerializedName() {
        return this.name;
    }

    /**
     * Gets the display slot corresponding to the given text color.
     *
     * @param textColor the text color
     * @return the display slot corresponding to the given text color, or null if none
     */
    // The method is not ideal because of the big switch statement. For the future we can update this
    public static @Nullable DisplaySlot byTextColor(@NotNull TextColor textColor) {
        Check.argCondition(!(textColor instanceof NamedTextColor), "TextColor must be a NamedTextColor (Minecraft limitation)");
        NamedTextColor namedTextColor = (NamedTextColor) textColor;
        return switch (namedTextColor.toString()) {
            case "black":
                yield TEAM_BLACK;
            case "dark_blue":
                yield TEAM_DARK_BLUE;
            case "dark_green":
                yield TEAM_DARK_GREEN;
            case "dark_aqua":
                yield TEAM_DARK_AQUA;
            case "dark_red":
                yield TEAM_DARK_RED;
            case "dark_purple":
                yield TEAM_DARK_PURPLE;
            case "gold":
                yield TEAM_GOLD;
            case "gray":
                yield TEAM_GRAY;
            case "dark_gray":
                yield TEAM_DARK_GRAY;
            case "blue":
                yield TEAM_BLUE;
            case "green":
                yield TEAM_GREEN;
            case "aqua":
                yield TEAM_AQUA;
            case "red":
                yield TEAM_RED;
            case "light_purple":
                yield TEAM_LIGHT_PURPLE;
            case "yellow":
                yield TEAM_YELLOW;
            case "white":
                yield TEAM_WHITE;
            default:
                yield null;
        };
    }
}
