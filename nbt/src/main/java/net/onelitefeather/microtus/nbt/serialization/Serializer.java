package net.onelitefeather.microtus.nbt.serialization;

import net.onelitefeather.microtus.nbt.Mode;
import net.onelitefeather.microtus.nbt.TagType;
import net.onelitefeather.microtus.nbt.tree.Tag;
import net.onelitefeather.microtus.nbt.tree.TagByte;
import net.onelitefeather.microtus.nbt.tree.TagByteArray;
import net.onelitefeather.microtus.nbt.tree.TagCompound;
import net.onelitefeather.microtus.nbt.tree.TagDouble;
import net.onelitefeather.microtus.nbt.tree.TagFloat;
import net.onelitefeather.microtus.nbt.tree.TagInt;
import net.onelitefeather.microtus.nbt.tree.TagIntArray;
import net.onelitefeather.microtus.nbt.tree.TagList;
import net.onelitefeather.microtus.nbt.tree.TagLongArray;
import net.onelitefeather.microtus.nbt.tree.TagRootCompound;
import net.onelitefeather.microtus.nbt.tree.TagShort;
import net.onelitefeather.microtus.nbt.tree.TagString;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import static net.onelitefeather.microtus.nbt.Mode.ModeImpl.FILE;
import static net.onelitefeather.microtus.nbt.Mode.ModeImpl.NETWORK;

public sealed interface Serializer permits Serializer.NbtSerializer {

    void serializeTree();

    static void serialize(FastDataOutputStream outputStream, Tag tag, Mode mode) {
        new NbtSerializer(outputStream, tag, mode).serializeTree();
    }

    static void serialize(BufferedOutputStream outputStream, Tag tag, Mode mode) {
        new NbtSerializer(new FastDataOutputStream(outputStream), tag, mode).serializeTree();
    }

    static void serializeGZIP(GZIPOutputStream outputStream, Tag tag, Mode mode) {
        new NbtSerializer(new FastDataOutputStream(outputStream), tag, mode).serializeTree();
    }

    static void serializeZLIB(DeflaterOutputStream outputStream, Tag tag, Mode mode) {
        new NbtSerializer(new FastDataOutputStream(outputStream), tag, mode).serializeTree();
    }

    record NbtSerializer(DataOutputStream outputStream, Tag tag, Mode mode) implements Serializer {
        private void writeTag(Tag current) {
            try {
                switch (current) {
                    case TagCompound tag -> {
                        for (var entry : tag.value().entrySet()) {
                            String name = entry.getKey();
                            Tag wrappedNbtTag = entry.getValue();

                            outputStream.write(TagType.byTagClass(wrappedNbtTag).id());
                            outputStream.writeUTF(name);
                            writeTag(wrappedNbtTag);
                        }
                        outputStream.write(TagType.END.id());
                    }
                    case TagByteArray tagByteArray -> {
                        byte[] value = tagByteArray.value();
                        outputStream.writeInt(value.length);
                        outputStream.write(value);
                    }
                    case TagLongArray tagLongArray -> {
                        long[] value = tagLongArray.value();
                        byte[] bytes = new byte[value.length * Integer.BYTES];
                        MemorySegment.copy(tagLongArray.segment(), ValueLayout.JAVA_INT_UNALIGNED,0,
                                MemorySegment.ofArray(bytes), ValueLayout.JAVA_INT_UNALIGNED.withOrder(ByteOrder.BIG_ENDIAN), 0,
                                value.length);
                        outputStream.writeInt(value.length);
                        outputStream.write(bytes);
                    }
                    case TagIntArray tagIntArray -> {
                        int[] value = tagIntArray.value();
                        byte[] bytes = new byte[value.length * Integer.BYTES];
                        MemorySegment.copy(tagIntArray.segment(), ValueLayout.JAVA_INT_UNALIGNED,0,
                                MemorySegment.ofArray(bytes), ValueLayout.JAVA_INT_UNALIGNED.withOrder(ByteOrder.BIG_ENDIAN), 0,
                                value.length);
                        outputStream.writeInt(value.length);
                        outputStream.write(bytes);
                    }
                    case TagList<?> tagList -> {
                        byte id = (byte) (tagList.value().isEmpty()
                                ? TagType.END.id()
                                : TagType.byTagClass(tagList.value().getFirst()).id());
                        outputStream.write(id);
                        outputStream.writeInt(tagList.value().size());
                        tagList.value().forEach(this::writeTag);
                    }
                    case TagByte tagByte -> outputStream.write(tagByte.value());
                    case TagShort tagShort -> outputStream.writeShort(tagShort.value());
                    case TagInt tagInt -> outputStream.writeInt(tagInt.value());
                    case TagFloat tagFloat -> outputStream.writeFloat(tagFloat.value());
                    case TagDouble tagDouble -> outputStream.writeDouble(tagDouble.value());
                    case TagString tagString -> outputStream.writeUTF(tagString.value());
                    default -> throw new IllegalStateException("Unexpected value: " + current);
                }
            } catch (IOException e) {
                throw wrappedError(e);
            }
        }

        @Override
        public void serializeTree() {
            try (outputStream) {
                if (mode.equals(NETWORK) && tag instanceof TagCompound) {
                    outputStream.write(TagType.COMPOUND.id());
                    writeTag(tag);
                } else if (mode.equals(FILE) && tag instanceof TagRootCompound compound) {
                    outputStream.write(TagType.COMPOUND.id());
                    outputStream.writeUTF(compound.name());
                    writeTag(compound.value());
                }
            } catch (IOException e) {
                throw wrappedError(e);
            }
        }
        private NBTSerializationException wrappedError(Exception e) {
            return new NBTSerializationException("Unexpected error during tree processing.", e);
        }
    }
}
