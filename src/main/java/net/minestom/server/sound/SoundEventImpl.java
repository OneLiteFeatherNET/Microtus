package net.minestom.server.sound;

import net.minestom.server.registry.Registry;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

record SoundEventImpl(NamespaceID namespace, int id) implements SoundEvent {
    private static final Registry.Container<SoundEvent> CONTAINER = Registry.createStaticContainer(Registry.Resource.SOUNDS, SoundEventImpl::createImpl);

    private static SoundEvent createImpl(String namespace, Registry.Properties properties) {
        return new SoundEventImpl(NamespaceID.from(namespace), properties.getInt("id"));
    }

    static SoundEvent get(@NotNull String namespace) {
        return CONTAINER.get(namespace);
    }

    static SoundEvent getSafe(@NotNull String namespace) {
        return CONTAINER.getSafe(namespace);
    }

    static SoundEvent getId(int id) {
        return CONTAINER.getId(id);
    }

    static Collection<SoundEvent> values() {
        return CONTAINER.values();
    }

    @Override
    public String toString() {
        return name();
    }
}
