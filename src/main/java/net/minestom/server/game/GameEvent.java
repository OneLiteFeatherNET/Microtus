package net.minestom.server.game;

import net.minestom.server.registry.Registry;
import net.minestom.server.registry.StaticProtocolObject;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public sealed interface GameEvent extends StaticProtocolObject permits GameEventImpl {

    /**
     * Returns the entity registry.
     *
     * @return the entity registry or null if it was created with a builder
     */
    @Contract(pure = true)
    @Nullable
    Registry.GameEventEntry registry();

    @Override
    @NotNull
    NamespaceID namespace();

    static @NotNull Collection<@NotNull GameEvent> values() {
        return GameEventImpl.values();
    }

    static @Nullable GameEvent fromNamespaceId(@NotNull String namespaceID) {
        return GameEventImpl.getSafe(namespaceID);
    }

    static @Nullable GameEvent fromNamespaceId(@NotNull NamespaceID namespaceID) {
        return fromNamespaceId(namespaceID.asString());
    }

}