package net.minestom.server.scoreboard.score.criteria;

import org.jetbrains.annotations.NotNull;

/**
 * The {@link ScoreboardObjectiveCriteria} record is the default implementation of the {@link ObjectiveCriteria} interface.
 *
 * @param name       the name of the criteria
 * @param renderType the render type of the criteria
 */
record ScoreboardObjectiveCriteria(@NotNull String name, @NotNull RenderType renderType) implements ObjectiveCriteria {
}
