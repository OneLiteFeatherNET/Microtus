package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.Viewable;
import net.minestom.server.adventure.audience.PacketGroupingAudience;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * This interface represents all scoreboard of Minecraft.
 */
public interface Scoreboard extends Viewable, PacketGroupingAudience {

    /**
     * Updates the score of a {@link Player}.
     *
     * @param player The player
     * @param score  The new score
     */
    default void updateScore(Player player, int score) {
        //todo
//        sendPacketsToViewers(new UpdateScorePacket(player.getUsername(), (byte) 0, getObjectiveName(), score));
    }

    /**
     * Gets the objective name of the scoreboard.
     *
     * @return the objective name
     */
    @NotNull String getObjectiveName();

    /**
     * Get the title as {@link Component} of the scoreboard.
     * @return the title of the scoreboard
     */
    @NotNull Component getTitle();

    @Override
    default @NotNull Collection<Player> getPlayers() {
        return this.getViewers();
    }
}
