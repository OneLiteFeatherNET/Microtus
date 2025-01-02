package net.minestom.server.item.banner;

import net.kyori.adventure.key.Key;
import net.minestom.server.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Microtus -  Banner and Shield Meta
public record BannerPatternImpl(Key namespace, int id, String identifier) implements BannerPattern {

    private static Map<String, BannerPatternImpl> IDENTIFIERS = new HashMap<>();
    private static final Registry.Container<BannerPattern> CONTAINER = Registry.createStaticContainer(Registry.Resource.BANNER_PATTERNS, BannerPatternImpl::createImpl);

    private static BannerPattern createImpl(String namespace, Registry.Properties properties) {
        int id = properties.getInt("id");
        String identifier = properties.getString("identifier");
        BannerPatternImpl bannerPattern = new BannerPatternImpl(Key.key(namespace), id, identifier);
        IDENTIFIERS.put(identifier, bannerPattern);
        return bannerPattern;
    }

    static BannerPattern get(@NotNull String namespace) {
        return CONTAINER.get(namespace);
    }

    static BannerPattern getSafe(@NotNull String namespace) {
        return CONTAINER.getSafe(namespace);
    }

    static BannerPattern getId(int id) {
        return CONTAINER.getId(id);
    }

    static BannerPattern getIdentifier(@NotNull String identifier) {
        return IDENTIFIERS.get(identifier);
    }

    static Collection<BannerPattern> values() {
        return CONTAINER.values();
    }

    @Override
    public String toString() {
        return name();
    }
}
