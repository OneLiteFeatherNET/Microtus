package net.onelitefeather.microtus.nbt.tree;

import java.lang.foreign.MemorySegment;
import java.util.Arrays;

public sealed interface TagIntArray extends Tag permits TagIntArray.TagIntArrayImpl {

    int[] value();

    MemorySegment segment();

    record TagIntArrayImpl(int[] value, MemorySegment segment) implements TagIntArray {
        TagIntArrayImpl(int[] value) {
            this(Arrays.copyOf(value, value.length), MemorySegment.ofArray(value).asReadOnly());
        }
    }
}
