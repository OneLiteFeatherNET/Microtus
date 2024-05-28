package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagShort extends Tag permits TagShort.TagShortImpl {

    short value();

    record TagShortImpl(short value) implements TagShort {

    }
}
