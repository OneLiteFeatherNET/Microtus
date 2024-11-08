package net.minestom.server.scoreboard;

import net.minestom.server.Viewable;
import net.minestom.server.adventure.audience.PacketGroupingAudience;
import net.minestom.server.scoreboard.objective.Objective;
import net.minestom.server.scoreboard.score.DisplaySlot;
import net.minestom.server.scoreboard.score.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.function.Consumer;

public interface ScoreboardContainer extends Viewable, PacketGroupingAudience {

    void addScore(@NotNull Score score);

    void addScores(@NotNull Score... scores);

    void replaceScore(int index, @NotNull Score score);

    void replaceScore(int index, Consumer<Score> mapper);

    void removeScore(int index);

    void removeScores(int... indices);

    void setDisplayObjective(@NotNull DisplaySlot slot, @NotNull Objective objective);

    boolean resetDisplayObjective(@NotNull DisplaySlot slot);

    @NotNull Objective getDisplayObjective(@NotNull DisplaySlot slot);

    @NotNull Objective addObjective(@NotNull Objective objective);

    @Nullable Objective getObjective(@Nullable String name);

    @Nullable Score getScore(int index);

    @NotNull @UnmodifiableView
    Collection<Objective> getObjectives();

    @NotNull @UnmodifiableView Collection<String> getObjectiveNames();
}
