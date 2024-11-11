package net.minestom.testing;

import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A FlexibleListener is a listener construct which works different from the normal listener code from Minestom.
 * The use case is to follow listener during a test and can be used that the event was called or not
 *
 * @param <E> the event type
 * @version 1.0.0
 * @since 1.0.0
 */
public interface FlexibleListener<E extends Event> {
    /**
     * Updates the handler. Fails if the previous followup has not been called.
     */
    void followup(@NotNull Consumer<E> handler);

    /**
     * Default followup which does nothing.
     */
    default void followup() {
        followup(event -> {
            // Empty
        });
    }

    /**
     * Fails if an event is received. Valid until the next followup call.
     */
    void failFollowup();
}
