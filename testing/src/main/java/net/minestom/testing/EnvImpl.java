package net.minestom.testing;

import net.minestom.server.ServerProcess;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The {@link EnvImpl} is the implementation of the {@link Env} interface.
 * It is used to create connections and more for the testing environment.
 *
 * @version 1.0.0
 * @since 1.0.0
 */
final class EnvImpl implements Env {

    private final ServerProcess process;
    private final List<FlexibleListenerImpl<?>> listeners = new CopyOnWriteArrayList<>();

    /**
     * Creates a new instance of {@link EnvImpl} with the given {@link ServerProcess}.
     *
     * @param process the process to use
     */
    public EnvImpl(@NotNull ServerProcess process) {
        this.process = process;
    }

    /**
     * Gets the {@link ServerProcess} used by this environment.
     *
     * @return the server process
     */
    @Override
    public @NotNull ServerProcess process() {
        return process;
    }

    /**
     * Creates a new connection.
     *
     * @return the created connection
     */
    @Override
    public @NotNull TestConnection createConnection() {
        return new TestConnectionImpl(this);
    }

    /**
     * Tracks a specific event type in the test environment.
     *
     * @param eventType the event type to track
     * @param filter    the filter to apply
     * @param actor     the actor to use
     * @param <E>       the event type
     * @param <H>       the actor type
     * @return the {@link Collector} instance to use
     */
    @Override
    public @NotNull <E extends Event, H> Collector<E> trackEvent(@NotNull Class<E> eventType, @NotNull EventFilter<? super E, H> filter, @NotNull H actor) {
        var tracker = new EventCollector<E>(actor);
        this.process.eventHandler().map(actor, filter).addListener(eventType, tracker.events::add);
        return tracker;
    }

    /**
     * Listens for a specific event type in the test environment.
     *
     * @param eventType the event type to listen for
     * @param <E>       the event type
     * @return the {@link FlexibleListener} instance from the event type
     */
    @Override
    public @NotNull <E extends Event> FlexibleListener<E> listen(@NotNull Class<E> eventType) {
        var handler = process.eventHandler();
        var flexible = new FlexibleListenerImpl<>(eventType);
        var listener = EventListener.of(eventType, e -> flexible.handler.accept(e));
        handler.addListener(listener);
        this.listeners.add(flexible);
        return flexible;
    }

    /**
     * Removes all listener which are currently registered in the test environment.
     */
    @Override
    public void cleanup() {
        this.listeners.forEach(FlexibleListenerImpl::check);
        this.process.stop();
    }

    /**
     * The class is the implementation of the {@link Collector} interface.
     *
     * @param <E> the event type
     */
    final class EventCollector<E extends Event> implements Collector<E> {

        private final Object handler;
        private final List<E> events = new CopyOnWriteArrayList<>();

        /**
         * Creates a new instance of an event collector with the given handler.
         *
         * @param handler the handler to use
         */
        public EventCollector(@NotNull Object handler) {
            this.handler = handler;
        }

        /**
         * Returns a list of all collected events.
         *
         * @return the list of events
         */
        @Override
        public @NotNull List<E> collect() {
            process.eventHandler().unmap(handler);
            return List.copyOf(events);
        }
    }

    /**
     * The class is the implementation of the {@link FlexibleListener} interface.
     *
     * @param <E> the event type
     */
    static final class FlexibleListenerImpl<E extends Event> implements FlexibleListener<E> {

        private final Class<E> eventType;
        private Consumer<E> handler = e -> {
        };
        private boolean initialized;
        private boolean called;

        /**
         * Creates a new instance of a flexible listener with the given event type.
         *
         * @param eventType the event type
         */
        FlexibleListenerImpl(@NotNull Class<E> eventType) {
            this.eventType = eventType;
        }

        /**
         * Updates the current handler reference.
         *
         * @param handler the handler to update
         */
        @Override
        public void followup(@NotNull Consumer<E> handler) {
            updateHandler(handler);
        }

        /**
         * Calls a fail assertions if the event can't be followed up.
         */
        @Override
        public void failFollowup() {
            updateHandler(e -> fail("Event " + e.getClass().getSimpleName() + " was not expected"));
        }

        /**
         * Updates the {@link Consumer} which acts as the handler.
         *
         * @param handler the handler to update
         */
        void updateHandler(@NotNull Consumer<E> handler) {
            check();
            this.initialized = true;
            this.called = false;
            this.handler = e -> {
                handler.accept(e);
                this.called = true;
            };
        }

        /**
         * Checks if the last listener has been called.
         */
        void check() {
            assertTrue(!initialized || called, "Last listener has not been called: " + eventType.getSimpleName());
        }
    }
}
