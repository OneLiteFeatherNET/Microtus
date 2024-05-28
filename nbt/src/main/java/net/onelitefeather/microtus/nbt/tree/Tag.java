package net.onelitefeather.microtus.nbt.tree;

import java.util.List;
import java.util.Map;

public sealed interface Tag permits TagByte, TagByteArray, TagCompound, TagDouble, TagFloat, TagInt, TagIntArray, TagList, TagLong, TagLongArray, TagRootCompound, TagShort, TagString {
    static TagByteArray byteArrayTag(byte... value) {
        return new TagByteArray.TagByteArrayImpl(value);
    }

    static TagLongArray longArrayTag(long... value) {
        return new TagLongArray.TagLongArrayImpl(value);
    }

    static TagIntArray intArrayTag(int... value) {
        return new TagIntArray.TagIntArrayImpl(value);
    }

    static TagByte byteTag(byte value) {
        return new TagByte.TagByteImpl(value);
    }

    static TagDouble doubleTag(double value) {
        return new TagDouble.TagDoubleImpl(value);
    }

    static TagFloat floatTag(float value) {
        return new TagFloat.TagFloatImpl(value);
    }

    static TagCompound compoundTag(Map<String, Tag> value) {
        return new TagCompound.TagCompoundImpl(value);
    }

    static TagInt intTag(int value) {
        return new TagInt.TagIntImpl(value);
    }

    static TagLong longTag(long value) {
        return new TagLong.TagLongImpl(value);
    }

    static TagShort shortTag(short value) {
        return new TagShort.TagShortImpl(value);
    }

    static TagString stringTag(String value) {
        return new TagString.TagStringImpl(value);
    }

    static TagRootCompound rootTag(String name, TagCompound compound) {
        return new TagRootCompound.TagRootCompoundImpl(name, compound);
    }

    static <T extends Tag> TagList<T> listOf(List<T> value) {
        return new TagList.TagListImpl<>(value);
    }

}
