package net.minestom.server.featureflag;

import net.minestom.server.registry.Registry;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public record FeatureFlagImpl(
        @NotNull Registry.FeatureFlagEntry registry,
        @NotNull NamespaceID namespace,
        int id
) implements FeatureFlag {
    private static final AtomicInteger INDEX = new AtomicInteger();
    private static final Registry.DynamicContainer<FeatureFlag> CONTAINER = Registry.createDynamicContainer(Registry.Resource.FEATURE_FLAGS, FeatureFlagImpl::createImpl);

    private static @NotNull FeatureFlagImpl createImpl(@NotNull String namespace, @NotNull Registry.Properties properties) {
        return new FeatureFlagImpl(Registry.featureFlag(namespace, properties));
    }

    private FeatureFlagImpl(@NotNull Registry.FeatureFlagEntry registry) {
        this(registry, registry.namespace(), INDEX.getAndIncrement());
    }

    static @NotNull Collection<FeatureFlag> values() {
        return CONTAINER.values();
    }

    public static @Nullable FeatureFlag get(@NotNull String namespace) {
        return CONTAINER.get(namespace);
    }

    static @Nullable FeatureFlag getSafe(@NotNull String namespace) {
        return CONTAINER.getSafe(namespace);
    }
}
