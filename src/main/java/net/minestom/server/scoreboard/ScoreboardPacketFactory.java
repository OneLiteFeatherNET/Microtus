package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.network.packet.server.play.DisplayScoreboardPacket;
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link ScoreboardPacketFactory} provides a fluent way to create specific packets related to the scoreboard.
 * It is used internally by the given scoreboard source to reduce the complexity of the system itself.
 * <p>
 * It fixes an issue that the {@link Scoreboard} interface acts like a facade and a factory at the same time.
 * To avoid such weird behavior, this class has been created to handle the creation of packets.
 * <p>>
 * **Note** Beware that this class is not intended to be used by the end-user.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
@ApiStatus.Internal
abstract class ScoreboardPacketFactory {

    /**
     * Creates the creation objective packet.
     *
     * @param objectiveName the name of the objective
     * @param value         the value of the objective
     * @param type          the type of the objective
     * @return the creation objective packet
     */
    @Contract(pure = true, value = "_, _, _ -> new")
    public static @NotNull ScoreboardObjectivePacket getCreationObjectivePacket(
            @NotNull String objectiveName,
            @NotNull Component value,
            @NotNull ScoreboardObjectivePacket.Type type
    ) {
        return new ScoreboardObjectivePacket(objectiveName, (byte) 0, value, type, null);
    }

    /**
     * Creates the creation objective packet.
     *
     * @param objectiveName the name of the objective
     * @return the creation objective packet
     */
    @Contract(pure = true, value = "_ -> new")
    public static @NotNull ScoreboardObjectivePacket getDestructionObjectivePacket(@NotNull String objectiveName) {
        return new ScoreboardObjectivePacket(objectiveName, (byte) 1, null, null, null);
    }

    /**
     * Creates the creation objective packet.
     *
     * @param scoreName the name of the objective
     * @param position      the position of the objective
     * @return the creation objective packet
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static @NotNull DisplayScoreboardPacket getDisplayScoreboardPacket(@NotNull String scoreName, byte position) {
        return new DisplayScoreboardPacket(position, scoreName);
    }

    private ScoreboardPacketFactory() {
        // No required for a factory
    }
}
