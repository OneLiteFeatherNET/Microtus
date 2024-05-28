package net.onelitefeather.microtus.nbt.tree;

public sealed interface TagFloat extends Tag permits TagFloat.TagFloatImpl {

    float value();

    record TagFloatImpl(float value) implements TagFloat {

    }
}
