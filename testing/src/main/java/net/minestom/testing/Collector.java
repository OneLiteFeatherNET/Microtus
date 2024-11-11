package net.minestom.testing;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@link Collector} is a utility class to collect elements and apply assertions to them.
 *
 * @param <T> the type for the elements
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Collector<T> {

    /**
     * Collects a list of elements
     *
     * @return the list of elements
     */
    @NotNull List<@NotNull T> collect();

    /**
     * Asserts that the collector contains exactly one element of the given type and applies the consumer to it.
     *
     * @param type     the type of the element
     * @param consumer the consumer to apply to the element
     */
    default <P extends  T> void assertSingle(@NotNull Class<P> type, @NotNull Consumer<P> consumer) {
        List<T> elements = collect();
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        var element = elements.getFirst();
        assertInstanceOf(type, element, "Expected type " + type.getSimpleName() + ", got " + element.getClass().getSimpleName());
        consumer.accept((P) element);
    }

    /**
     * Asserts that the collector contains exactly one element and applies the consumer to it.
     * The consumer can be sued to inject own assertions to the element.
     *
     * @param consumer the consumer to apply to the element
     */
    default void assertSingle(@NotNull Consumer<T> consumer) {
        List<T> elements = collect();
        assertEquals(1, elements.size(), "Expected 1 element, got " + elements);
        consumer.accept(elements.getFirst());
    }

    /**
     * Asserts that the collector contains the given amount of elements.
     *
     * @param count the expected amount of elements
     */
    default void assertCount(int count) {
        List<T> elements = collect();
        assertEquals(count, elements.size(), "Expected " + count + " element(s), got " + elements.size() + ": " + elements);
    }

    /**
     * Asserts that the collector contains exactly one element.
     */
    default void assertSingle() {
        assertCount(1);
    }

    /**
     * Asserts that the collector is empty.
     */
    default void assertEmpty() {
        assertCount(0);
    }
}
