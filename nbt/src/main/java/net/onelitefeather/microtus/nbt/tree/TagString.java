package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagString extends Tag permits TagString.TagStringImpl {

    String value();

    record TagStringImpl(String value) implements TagString {

    }
}
