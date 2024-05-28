package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagDouble extends Tag permits TagDouble.TagDoubleImpl {

    double value();

    record TagDoubleImpl(double value) implements TagDouble {

    }
}
