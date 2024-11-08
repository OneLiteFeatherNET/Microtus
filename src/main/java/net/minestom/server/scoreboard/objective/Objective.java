package net.minestom.server.scoreboard.objective;

import net.kyori.adventure.text.Component;
import net.minestom.server.scoreboard.format.NumberFormat;
import net.minestom.server.scoreboard.score.criteria.ObjectiveCriteria;
import net.minestom.server.scoreboard.score.criteria.RenderType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Objective} represents an objective from a {@link net.minestom.server.scoreboard.Scoreboard}.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
public sealed interface Objective permits ScoreboardObjective {

    /**
     * Creates a new {@link Objective} builder.
     *
     * @return the created {@link Builder} reference
     */
    @Contract(pure = true)
    static @NotNull Builder builder() {
        return new ObjectiveBuilder();
    }

    /**
     * Creates a new {@link Objective} builder with the provided objective.
     *
     * @param objective the objective to copy
     * @return the created {@link Builder} reference
     */
    @Contract(pure = true, value = "_ -> new")
    static @NotNull Builder builder(@NotNull Objective objective) {
        return new ObjectiveBuilder(objective);
    }

    /**
     * Updates the display name of this objective.
     *
     * @param component the new display name
     */
    void updateDisplayName(@NotNull Component component);

    /**
     * Updates the render type of this objective.
     *
     * @param renderType the new render type
     */
    void updateRenderType(@NotNull RenderType renderType);

    /**
     * Updates the number format of this objective.
     *
     * @param numberFormat the new number format
     */
    void updateNumberFormat(@NotNull NumberFormat numberFormat);

    /**
     * Gets the name of this objective.
     *
     * @return the name
     */
    @NotNull String name();

    /**
     * Gets the criteria of this objective.
     *
     * @return the criteria
     */
    @NotNull ObjectiveCriteria criteria();

    /**
     * Gets the display name of this objective.
     *
     * @return the display name, or {@code null} if not set
     */
    @Nullable Component displayName();

    /**
     * Gets the render type of this objective.
     *
     * @return the render type, or {@code null} if not set
     */
    @Nullable RenderType renderType();

    /**
     * Gets the number format of this objective.
     *
     * @return the number format, or {@code null} if not set
     */
    @Nullable NumberFormat numberFormat();

    /**
     * Builder definition to create a new {@link Objective} instance.
     */
    sealed interface Builder permits ObjectiveBuilder {

        /**
         * Sets the name of the objective.
         *
         * @param name the name
         * @return this builder, for chaining
         */
        @NotNull Builder name(@NotNull String name);

        /**
         * Sets the criteria of the objective.
         *
         * @param criteria the criteria
         * @return this builder, for chaining
         */
        @NotNull Builder criteria(@NotNull ObjectiveCriteria criteria);

        /**
         * Sets the display name of the objective.
         *
         * @param displayName the display name
         * @return this builder, for chaining
         */
        @NotNull Builder displayName(@Nullable Component displayName);

        /**
         * Sets the render type of the objective.
         *
         * @param renderType the render type
         * @return this builder, for chaining
         */
        @NotNull Builder renderType(@Nullable RenderType renderType);

        /**
         * Sets the number format of the objective.
         *
         * @param numberFormat the number format
         * @return this builder, for chaining
         */
        @NotNull Builder numberFormat(@Nullable NumberFormat numberFormat);

        /**
         * Creates a new {@link Objective} instance with the provided information.
         *
         * @return a new objective
         */
        @NotNull Objective build();
    }
}
