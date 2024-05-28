package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagByte extends Tag permits TagByte.TagByteImpl {

    byte value();

    record TagByteImpl(byte value) implements TagByte {

    }
}
