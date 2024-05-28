package net.onelitefeather.microtus.nbt.tree;

import java.lang.foreign.MemorySegment;
import java.util.Arrays;

public sealed interface TagLongArray extends Tag permits TagLongArray.TagLongArrayImpl {

    long[] value();

    MemorySegment segment();

    record TagLongArrayImpl(long[] value, MemorySegment segment) implements TagLongArray {
        TagLongArrayImpl(long[] value) {
            this(Arrays.copyOf(value, value.length), MemorySegment.ofArray(value).asReadOnly());
        }
    }
}
