package net.onelitefeather.microtus.nbt.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public sealed interface TagCompound extends Tag, Iterable<Map.Entry<String, Tag>> permits TagCompound.TagCompoundImpl {

    Map<String, Tag> value();

    Set<String> keys();

    Collection<Tag> values();

    boolean containsKey(String key);

    boolean containsValue(Tag tag);

    boolean isEmpty();

    <T extends Tag> T get(String key);

    record TagCompoundImpl(Map<String, Tag> value, Set<String> keys, Collection<Tag> values) implements TagCompound {
        TagCompoundImpl(Map<String, Tag> value) {
            this(value, value.keySet(), value.values());
        }

        @Override
        public boolean containsKey(String key) {
            return value.containsKey(key);
        }

        @Override
        public boolean containsValue(Tag tag) {
            return value.containsValue(tag);
        }

        @Override
        public boolean isEmpty() {
            return value.isEmpty();
        }

        @Override
        public <T extends Tag> T get(String key) {
            return (T) value.get(key);
        }


        @Override
        public Iterator<Map.Entry<String, Tag>> iterator() {
            return value.entrySet().iterator();
        }
    }
}
