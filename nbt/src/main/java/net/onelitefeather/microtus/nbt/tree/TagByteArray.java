package net.onelitefeather.microtus.nbt.tree;

import java.lang.foreign.MemorySegment;
import java.util.Arrays;

public sealed interface TagByteArray extends Tag permits TagByteArray.TagByteArrayImpl {

    byte[] value();

    MemorySegment segment();

    record TagByteArrayImpl(byte[] value, MemorySegment segment) implements TagByteArray {
        TagByteArrayImpl(byte[] value) {
            this(Arrays.copyOf(value, value.length), MemorySegment.ofArray(value).asReadOnly());
        }
    }
}
