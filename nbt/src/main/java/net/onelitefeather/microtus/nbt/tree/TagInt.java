package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagInt extends Tag permits TagInt.TagIntImpl {

    int value();

    record TagIntImpl(int value) implements TagInt {

    }
}
