package net.minestom.server.scoreboard.score.criteria;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the render type of {@link ObjectiveCriteria}.
 *
 * @version 1.0.0
 * @since 1.6.0
 */
public enum RenderType {

    INTEGER("integer"),
    HEARTS("hearts");

    private final String id;

    /**
     * Creates a new render type.
     *
     * @param id the render type id
     */
    RenderType(final String id) {
        this.id = id;
    }

    /**
     * Gets the render type id.
     *
     * @return the render type id
     */
    public @NotNull String getId() {
        return this.id;
    }
}
