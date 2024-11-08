package net.minestom.server.scoreboard;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.ScoreboardObjectivePacket;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents the {@link Player} tab list as a {@link Scoreboard}.
 */
public class TabList implements Scoreboard {

    /**
     * <b>WARNING:</b> You shouldn't create scoreboards with the same prefix as those
     */
    private static final String TAB_LIST_PREFIX = "tl-";

    private final Set<Player> viewers = new CopyOnWriteArraySet<>();
    private final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);
    private final String objectiveName;

    private ScoreboardObjectivePacket.Type type;

    public TabList(@NotNull String name, @NotNull ScoreboardObjectivePacket.Type type) {
        this.objectiveName = TAB_LIST_PREFIX + name;
        this.type = type;
    }

    /**
     * Gets the scoreboard objective type
     *
     * @return the scoreboard objective type
     */
    public ScoreboardObjectivePacket.Type getType() {
        return type;
    }

    /**
     * Changes the scoreboard objective type
     *
     * @param type The new type for the objective
     */
    public void setType(@NotNull ScoreboardObjectivePacket.Type type) {
        this.type = type;
    }

    @Override
    public boolean addViewer(@NotNull Player player) {
        final boolean result = this.viewers.add(player);
        if (result) {
            player.sendPacket(ScoreboardPacketFactory.getCreationObjectivePacket(getObjectiveName(), Component.empty(), this.type));
            player.sendPacket(ScoreboardPacketFactory.getDisplayScoreboardPacket(getObjectiveName(), (byte) 0));
        }
        return result;
    }

    @Override
    public boolean removeViewer(@NotNull Player player) {
        final boolean result = this.viewers.remove(player);
        if (result) {
            player.sendPacket(ScoreboardPacketFactory.getDestructionObjectivePacket(getObjectiveName()));
        }
        return result;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.empty();
    }

    @Override
    public @NotNull Set<Player> getViewers() {
        return unmodifiableViewers;
    }

    @Override
    public @NotNull String getObjectiveName() {
        return this.objectiveName;
    }
}
