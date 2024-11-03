package net.minestom.server.game;

import net.minestom.server.registry.Registry;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public record GameEventImpl(Registry.GameEventEntry registry, NamespaceID namespace, int id) implements GameEvent {
    private static final AtomicInteger INDEX = new AtomicInteger();
    private static final Registry.Container<GameEvent> CONTAINER = Registry.createStaticContainer(Registry.Resource.GAMEPLAY_TAGS, GameEventImpl::createImpl);

    private static GameEventImpl createImpl(String namespace, Registry.Properties properties) {
        return new GameEventImpl(Registry.gameEventEntry(namespace, properties));
    }

    private GameEventImpl(Registry.GameEventEntry registry) {
        this(registry, registry.namespace(), INDEX.getAndIncrement());
    }

    static Collection<GameEvent> values() {
        return CONTAINER.values();
    }

    public static GameEvent get(@NotNull String namespace) {
        return CONTAINER.get(namespace);
    }

    static GameEvent getSafe(@NotNull String namespace) {
        return CONTAINER.getSafe(namespace);
    }
}
