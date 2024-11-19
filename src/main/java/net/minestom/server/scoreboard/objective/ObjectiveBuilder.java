package net.minestom.server.scoreboard.objective;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.Sidebar;
import net.minestom.server.scoreboard.score.criteria.ObjectiveCriteria;
import net.minestom.server.scoreboard.score.criteria.RenderType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ObjectiveBuilder implements Objective.Builder {

    private String name;
    private ObjectiveCriteria criteria;
    private Component displayName;
    private RenderType renderType;
    private Sidebar.NumberFormat numberFormat;

    public ObjectiveBuilder() {
        this.renderType = RenderType.INTEGER;
    }

    /**
     * Creates a new {@link ObjectiveBuilder} with the given {@link Objective}.
     *
     * @param objective the objective to copy
     */
    ObjectiveBuilder(@NotNull Objective objective) {
        this.name = objective.name();
        this.criteria = objective.criteria();
        this.displayName = objective.displayName();
        this.renderType = objective.renderType();
        this.numberFormat = objective.numberFormat();
    }

    @Override
    public Objective.@NotNull Builder name(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public Objective.@NotNull Builder criteria(@NotNull ObjectiveCriteria criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public Objective.@NotNull Builder displayName(@Nullable Component displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public Objective.@NotNull Builder renderType(@Nullable RenderType renderType) {
        this.renderType = renderType;
        return this;
    }

    @Override
    public Objective.@NotNull Builder numberFormat(@Nullable Sidebar.NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }

    @Override
    public @NotNull Objective build() {
        return new ScoreboardObjective(this.name, this.criteria, this.displayName, this.renderType, this.numberFormat);
    }
}
