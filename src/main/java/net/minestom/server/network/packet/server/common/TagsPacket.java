package net.minestom.server.network.packet.server.common;

import net.minestom.server.gametag.BlockTag;
import net.minestom.server.gametag.EntityTag;
import net.minestom.server.gametag.FluidTag;
import net.minestom.server.gametag.GameEventTag;
import net.minestom.server.gametag.ItemTag;
import net.minestom.server.gametag.Tag;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minestom.server.network.NetworkBuffer.*;

public record TagsPacket(
        @NotNull Map<NamespaceID, List<Tag>> tagsMap) implements ServerPacket.Configuration, ServerPacket.Play {

    public static @NotNull Map<NamespaceID, List<Tag>> createDefaultTags() {
        Map<NamespaceID, List<Tag>> tagsMap = new HashMap<>();
        tagsMap.put(NamespaceID.from(BlockTag.IDENTIFIER), new ArrayList<>(BlockTag.values()));
        tagsMap.put(NamespaceID.from(ItemTag.IDENTIFIER), new ArrayList<>(ItemTag.values()));
        tagsMap.put(NamespaceID.from(FluidTag.IDENTIFIER), new ArrayList<>(FluidTag.values()));
        tagsMap.put(NamespaceID.from(EntityTag.IDENTIFIER), new ArrayList<>(EntityTag.values()));
        // tagsMap.put(NamespaceID.from(GameEventTag.IDENTIFIER), new ArrayList<>(GameEventTag.values()));
        return tagsMap;
    }

    public TagsPacket {
        tagsMap = Map.copyOf(tagsMap);
    }

    public TagsPacket(@NotNull NetworkBuffer reader) {
        this(readTagsMap(reader));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(VAR_INT, tagsMap.size());
        for (var entry : tagsMap.entrySet()) {
            final var type = entry.getKey();
            final var tags = entry.getValue();
            writer.write(STRING, type.asString());
            writer.write(VAR_INT, tags.size());
            for (var tag : tags) {
                writer.write(STRING, tag.namespace().asString());
                final var values = tag.tagValues();
                writer.write(VAR_INT, values.size());
                for (var name : values) {
                    writer.write(VAR_INT, tag.getMapper().apply(name));
                }
            }
        }
    }

    @Override
    public int configurationId() {
        return ServerPacketIdentifier.CONFIGURATION_TAGS;
    }

    @Override
    public int playId() {
        return ServerPacketIdentifier.TAGS;
    }

    private static Map<NamespaceID, List<Tag>> readTagsMap(@NotNull NetworkBuffer reader) {
        Map<NamespaceID, List<Tag>> tagsMap = new HashMap<>();
        // Read amount of tag types
        final int typeCount = reader.read(VAR_INT);
        for (int i = 0; i < typeCount; i++) {
            // Read tag type
            final String id = reader.read(STRING);
            // final Tag.BasicType tagType = Tag.BasicType.fromIdentifer(reader.read(STRING));
            // if (tagType == null) {
            //     throw new IllegalArgumentException("Tag type could not be resolved");
            // }

            final int tagCount = reader.read(VAR_INT);
            for (int j = 0; j < tagCount; j++) {
                final String tagName = reader.read(STRING);
                final int[] entries = reader.read(VAR_INT_ARRAY);
                // TODO convert
            }
        }
        return tagsMap;
    }
}
