package net.onelitefeather.microtus.nbt.tree;

import java.util.List;

public sealed interface TagList<T extends Tag> extends Tag permits TagList.TagListImpl {

    List<T> value();

    record TagListImpl<T extends Tag>(List<T> value) implements TagList<T> {

    }
}
