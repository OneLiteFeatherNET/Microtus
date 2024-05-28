package net.onelitefeather.microtus.nbt;

import net.onelitefeather.microtus.nbt.tree.Tag;
import net.onelitefeather.microtus.nbt.tree.TagByte;
import net.onelitefeather.microtus.nbt.tree.TagByteArray;
import net.onelitefeather.microtus.nbt.tree.TagCompound;
import net.onelitefeather.microtus.nbt.tree.TagDouble;
import net.onelitefeather.microtus.nbt.tree.TagFloat;
import net.onelitefeather.microtus.nbt.tree.TagInt;
import net.onelitefeather.microtus.nbt.tree.TagIntArray;
import net.onelitefeather.microtus.nbt.tree.TagList;
import net.onelitefeather.microtus.nbt.tree.TagLong;
import net.onelitefeather.microtus.nbt.tree.TagLongArray;
import net.onelitefeather.microtus.nbt.tree.TagShort;
import net.onelitefeather.microtus.nbt.tree.TagString;

public enum TagType {
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    private final int id;

    TagType(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static TagType byTagClass(Tag tag) {
        return switch (tag) {
            case TagByte tagByte -> BYTE;
            case TagShort tagShort -> SHORT;
            case TagInt tagInt -> INT;
            case TagLong tagLong -> LONG;
            case TagFloat tagFloat -> FLOAT;
            case TagDouble tagDouble -> DOUBLE;
            case TagByteArray tagByteArray -> BYTE_ARRAY;
            case TagString tagString -> STRING;
            case TagList<?> tagList -> LIST;
            case TagCompound tagCompound -> COMPOUND;
            case TagIntArray tagIntArray -> INT_ARRAY;
            case TagLongArray tagLongArray -> LONG_ARRAY;
            default -> throw new IllegalStateException("Unexpected value: " + tag);
        };
    }

    public static TagType byId(int id) {
        return switch (id) {
            case 0 -> END;
            case 1 -> BYTE;
            case 2 -> SHORT;
            case 3 -> INT;
            case 4 -> LONG;
            case 5 -> FLOAT;
            case 6 -> DOUBLE;
            case 7 -> BYTE_ARRAY;
            case 8 -> STRING;
            case 9 -> LIST;
            case 10 -> COMPOUND;
            case 11 -> INT_ARRAY;
            case 12 -> LONG_ARRAY;
            default -> throw new IllegalArgumentException("Unknown  id %s".formatted(id));
        };
    }
}
