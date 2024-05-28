package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagLong extends Tag permits TagLong.TagLongImpl {

    long value();

    record TagLongImpl(long value) implements TagLong {

    }
}
