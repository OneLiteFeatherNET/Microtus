package net.minestom.server.scoreboard.objective;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.format.NumberFormat;
import net.minestom.server.scoreboard.score.criteria.ObjectiveCriteria;
import net.minestom.server.scoreboard.score.criteria.RenderType;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ScoreboardObjective implements Objective {

    private final String name;
    private final ObjectiveCriteria criteria;
    private Component displayName;
    private RenderType renderType;
    private NumberFormat numberFormat;

    ScoreboardObjective(
            @NotNull String name,
            @NotNull ObjectiveCriteria criteria,
            @NotNull Component displayName,
            @NotNull RenderType renderType,
            @NotNull NumberFormat numberFormat
    ) {
        Check.argCondition(name.trim().isEmpty(), "The name cannot be empty");
        this.name = name;
        this.criteria = criteria;
        this.displayName = displayName;
        this.renderType = renderType;
        this.numberFormat = numberFormat;
    }

    @Override
    public void updateDisplayName(@NotNull Component component) {
        this.displayName = component;
    }

    @Override
    public void updateRenderType(@NotNull RenderType renderType) {
        this.renderType = renderType;
    }

    @Override
    public void updateNumberFormat(@NotNull NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }

    @Override
    public @NotNull ObjectiveCriteria criteria() {
        return this.criteria;
    }

    @Override
    public @Nullable Component displayName() {
        return this.displayName;
    }

    @Override
    public @Nullable RenderType renderType() {
        return this.renderType;
    }

    @Override
    public @Nullable NumberFormat numberFormat() {
        return this.numberFormat;
    }
}
