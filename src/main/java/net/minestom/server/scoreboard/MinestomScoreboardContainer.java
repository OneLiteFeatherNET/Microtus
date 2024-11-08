package net.minestom.server.scoreboard;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.objective.Objective;
import net.minestom.server.scoreboard.score.DisplaySlot;
import net.minestom.server.scoreboard.score.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class MinestomScoreboardContainer implements ScoreboardContainer {

    private final Object2ObjectMap<String, Objective> objectivesByName;
    private final List<Score> scores;
    private final Map<DisplaySlot, Objective> displayObjectives;
    private final Set<Player> viewers;

    public MinestomScoreboardContainer() {
        this.objectivesByName = new Object2ObjectOpenHashMap<>(16, 0.5F);
        this.scores = new ObjectArrayList<>(16);
        this.displayObjectives = new EnumMap<>(DisplaySlot.class);
        this.viewers = new HashSet<>();
    }

    @Override
    public boolean addViewer(@NotNull Player player) {
        if (this.viewers.add(player)) {
            //TODO: Send packets
            return true;
        }
        return false;
    }

    @Override
    public boolean removeViewer(@NotNull Player player) {
        if (this.viewers.remove(player)) {
            //TODO: Send packets
        }
        return false;
    }

    @Override
    public void addScore(@NotNull Score score) {
        this.scores.add(score);
    }

    @Override
    public void addScores(@NotNull Score... scores) {
        if (scores.length == 0) return;
        for (int i = 0; i < scores.length; i++) {
            addScore(scores[i]);
        }
    }

    @Override
    public void replaceScore(int index, @NotNull Score score) {
        this.scores.set(index, score);
    }

    @Override
    public void replaceScore(int index, @NotNull Consumer<Score> mapper) {
        Score dataScore = this.scores.get(index);
        if (dataScore == null) {
            throw new IllegalArgumentException("No score at index " + index);
        }
        mapper.accept(dataScore);
        this.scores.add(index, dataScore);
    }

    @Override
    public void removeScore(int index) {
        this.scores.remove(index);
    }

    @Override
    public void removeScores(int... indices) {
        if (indices.length == 0) return;
        for (int i = 0; i < indices.length; i++) {
            removeScore(indices[i]);
        }
    }

    @Override
    public void setDisplayObjective(@NotNull DisplaySlot slot, @NotNull Objective objective) {
        displayObjectives.put(slot, objective);
    }

    @Override
    public boolean resetDisplayObjective(@NotNull DisplaySlot slot) {
        return displayObjectives.remove(slot) != null;
    }

    @Override
    public @NotNull Objective getDisplayObjective(@NotNull DisplaySlot slot) {
        return displayObjectives.get(slot);
    }

    @Override
    public @NotNull Objective addObjective(@NotNull Objective objective) {
        return this.objectivesByName.putIfAbsent(objective.name(), objective);
    }

    @Override
    public @Nullable Objective getObjective(@Nullable String name) {
        return objectivesByName.get(name);
    }

    @Override
    public @Nullable Score getScore(int index) {
        if (index < 0 || index >= scores.size()) return null;
        return scores.get(index);
    }

    @Override
    public @NotNull @UnmodifiableView Collection<Objective> getObjectives() {
        return Collections.unmodifiableCollection(objectivesByName.values());
    }

    @Override
    public @NotNull @UnmodifiableView Collection<String> getObjectiveNames() {
        return Collections.unmodifiableCollection(objectivesByName.keySet());
    }

    @Override
    public @NotNull Collection<@NotNull Player> getPlayers() {
        return this.getViewers();
    }

    @Override
    public @NotNull Set<@NotNull Player> getViewers() {
        return Collections.unmodifiableSet(viewers);
    }
}
