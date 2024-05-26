package net.minestom.server.gametag;

import net.minestom.server.registry.Registry;
import net.minestom.server.registry.StaticProtocolObject;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;

public sealed interface Tag extends StaticProtocolObject permits BlockTag, EntityTag, FluidTag, GameEventTag, ItemTag {

    @Contract(pure = true)
    @NotNull Registry.TagEntry registry();

    @Override
    default @NotNull NamespaceID namespace() {
        return registry().namespace();
    }

    @NotNull Collection<@NotNull NamespaceID> tagValues();

    @NotNull Function<NamespaceID, Integer> getMapper();
}
