package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagRootCompound extends Tag {

    String name();

    TagCompound value();

    record TagRootCompoundImpl(String name, TagCompound value) implements TagRootCompound {}
}
