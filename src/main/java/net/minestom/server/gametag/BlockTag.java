package net.minestom.server.gametag;

import net.minestom.server.instance.block.Block;
import net.minestom.server.registry.Registry;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public record BlockTag(Registry.TagEntry registry, int id, List<String> stringValue) implements Tag, BlockTags {
    private static final AtomicInteger INDEX = new AtomicInteger();
    private static final Function<NamespaceID, Integer> TAG_TO_ID = namespaceID -> Block.fromNamespaceId(namespaceID).id();
    private static final Registry.Container<Tag> CONTAINER = Registry.createStaticContainer(Registry.Resource.BLOCK_TAGS, BlockTag::createImpl);

    public static final String IDENTIFIER = "minecraft:block";

    public BlockTag(Registry.TagEntry registry) {
        this(registry, INDEX.getAndIncrement(), registry.values());
    }

    private static Tag createImpl(String namespace, Registry.Properties properties) {
        return new BlockTag(Registry.tag(namespace, properties));
    }

    static Tag get(String namespace) {
        return CONTAINER.get(namespace);
    }

    public static @NotNull Collection<Tag> values() {
        return CONTAINER.values();
    }

    @Override
    public @NotNull Collection<@NotNull NamespaceID> tagValues() {
        var concatinatedTags = stringValue.stream().filter(s -> s.startsWith("#")).toList();
        var result = new ArrayList<>(stringValue.stream().filter(s -> !s.startsWith("#")).map(NamespaceID::from).toList());
        concatinatedTags.stream().map(BlockTag::getSafe).filter(Objects::nonNull).map(Tag::tagValues).forEach(result::addAll);
        return result;
    }

    @Override
    public @NotNull Function<NamespaceID, Integer> getMapper() {
        return TAG_TO_ID;
    }

    static Tag getSafe(@NotNull String namespace) {
        return CONTAINER.getSafe(namespace);
    }

}
