package net.minestom.server.scoreboard.score.criteria;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * //TODO: Docs
 */
public sealed interface ObjectiveCriteria permits ScoreboardObjectiveCriteria {

    /**
     * Creates a new objective criteria with the specified name and render type.
     *
     * @param name the name of the criteria
     * @param type the render type of the criteria
     * @return a new objective criteria
     */
    @Contract(pure = true, value = "_, _ -> new")
    static @NotNull ObjectiveCriteria of(@NotNull String name, @NotNull RenderType type) {
        return new ScoreboardObjectiveCriteria(name, type);
    }

    /**
     * Creates a new objective criteria with the specified name and render type {@link RenderType#INTEGER}.
     *
     * @param name the name of the criteria
     * @return a new objective criteria
     */
    @Contract(pure = true, value = "_ -> new")
    static @NotNull ObjectiveCriteria of(@NotNull String name) {
        return new ScoreboardObjectiveCriteria(name, RenderType.INTEGER);
    }

    /**
     * Gets the name of the criteria.
     *
     * @return the name of the criteria
     */
    @NotNull String name();

    /**
     * Gets the render type of the criteria.
     *
     * @return the render type of the criteria
     */
    @NotNull RenderType renderType();
}
