package net.onelitefeather.microtus.nbt.deserialization;

import net.onelitefeather.microtus.nbt.Mode;
import net.onelitefeather.microtus.nbt.TagType;
import net.onelitefeather.microtus.nbt.tree.Tag;
import net.onelitefeather.microtus.nbt.tree.TagCompound;
import net.onelitefeather.microtus.nbt.tree.TagList;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import static net.onelitefeather.microtus.nbt.Mode.ModeImpl.FILE;
import static net.onelitefeather.microtus.nbt.Mode.ModeImpl.NETWORK;
import static net.onelitefeather.microtus.nbt.Mode.file;

public sealed interface Deserializer {

    Tag deserializeBytes();

    static Deserializer deserialize(PushbackInputStream inputStream, Mode mode) throws IOException {
        if (mode == file()) {
            if (isGzip(inputStream)) {
                return deserialize(new GZIPInputStream(inputStream, 512), mode);
            }
            if (isZlib(inputStream)) {
                return deserialize(new InflaterInputStream(inputStream), mode);
            }
        }
        return new NbtSerializer(new DataInputStream(inputStream), mode);
    }

    static Deserializer deserialize(BufferedInputStream inputStream, Mode mode) {
        return new NbtSerializer(new DataInputStream(inputStream), mode);
    }

    static Deserializer deserialize(GZIPInputStream inputStream, Mode mode) {
        return new NbtSerializer(new DataInputStream(inputStream), mode);
    }

    static Deserializer deserialize(InflaterInputStream inputStream, Mode mode) {
        return new NbtSerializer(new DataInputStream(inputStream), mode);
    }

    static Deserializer deserialize(DataInputStream inputStream, Mode mode) {
        return new NbtSerializer(inputStream, mode);
    }

    record NbtSerializer(DataInputStream inputStream, Mode mode) implements Deserializer {

        @Override
        public Tag deserializeBytes() {
            try (inputStream) {
                if (readType() != TagType.COMPOUND) throw error("NBT Data has to start with a compound");

                return switch (mode) {
                    case NETWORK -> compound();
                    case FILE -> Tag.rootTag(inputStream.readUTF(), compound());
                };

            } catch (IOException e) {
                throw wrappedError(e);
            }
        }

        private Tag deserialize(TagType type) throws IOException {
            return switch (type) {
                case END -> throw error("TAG_END isn't allowed to be wrapped in a named tag");
                case BYTE -> Tag.byteTag(inputStream.readByte());
                case SHORT -> Tag.shortTag(inputStream.readShort());
                case INT -> Tag.intTag(inputStream.readInt());
                case LONG -> Tag.longTag(inputStream.readLong());
                case FLOAT -> Tag.floatTag(inputStream.readFloat());
                case DOUBLE -> Tag.doubleTag(inputStream.readDouble());
                case STRING -> Tag.stringTag(inputStream.readUTF());
                case LIST -> list();
                case COMPOUND -> compound();
                case BYTE_ARRAY -> Tag.byteArrayTag(readBytes(Byte.BYTES));
                case INT_ARRAY -> {
                    byte[] bytes = readBytes(Integer.BYTES);
                    int[] integers = new int[bytes.length / Integer.BYTES];
                    MemorySegment.copy(MemorySegment.ofArray(bytes), ValueLayout.JAVA_INT_UNALIGNED.withOrder(ByteOrder.BIG_ENDIAN), 0,
                            MemorySegment.ofArray(integers), ValueLayout.OfInt.JAVA_INT_UNALIGNED, 0,
                            integers.length);
                    yield Tag.intArrayTag(integers);
                }
                case LONG_ARRAY -> {
                    byte[] bytes = readBytes(Long.BYTES);
                    long[] longs = new long[bytes.length / Long.BYTES];
                    MemorySegment.copy(MemorySegment.ofArray(bytes), ValueLayout.JAVA_LONG_UNALIGNED.withOrder(ByteOrder.BIG_ENDIAN), 0,
                            MemorySegment.ofArray(longs), ValueLayout.OfInt.JAVA_LONG_UNALIGNED, 0,
                            longs.length);
                    yield Tag.longArrayTag(longs);
                }
            };
        }

        private TagList<?> list() throws IOException {
            var type = readType();
            int length = inputStream.readInt();
            var tags = new ArrayList<Tag>(length);
            for (int i = 0; i < length; i++) {
                tags.add(deserialize(type));
            }
            return Tag.listOf(tags);
        }

        private byte[] readBytes(int typeSize) throws IOException {
            int length = inputStream.readInt() * typeSize;
            var bytes = new byte[length];
            inputStream.readFully(bytes);
            return bytes;
        }

        private TagCompound compound() throws IOException {
            var tags = new HashMap<String, Tag>();
            while (true) {
                TagType type = readType();
                if (type == TagType.END) break;
                String name = inputStream.readUTF();
                Tag payload = deserialize(type);

                tags.put(name, payload);
            }
            return Tag.compoundTag(tags);
        }

        private TagType readType() throws IOException {
            return TagType.byId(inputStream.readByte());
        }

        private NBTDeserializationException wrappedError(Exception e) {
            return new NBTDeserializationException("Unexpected error during tree processing.", e);
        }

        private NBTDeserializationException error(String msg) {
            return new NBTDeserializationException(-1, msg);
        }
    }

    private static boolean isZlib(PushbackInputStream stream) throws IOException {
        byte[] bs = new byte[2];
        boolean isZlib = false;
        int bytesRead = stream.read(bs, 0, 2);

        if (bytesRead == 2) {
            int cmf = bs[0] & 0xFF;
            if (cmf == 0x78) { // Checking if the first byte is 0x78 which is typical for zlib
                isZlib = true;
            }
        }

        if (bytesRead != 0) {
            stream.unread(bs, 0, bytesRead);
        }

        return isZlib;
    }

    private static boolean isGzip(PushbackInputStream stream) throws IOException {
        byte[] bs = new byte[2];
        boolean bl = false;
        int i = stream.read(bs, 0, 2);
        if (i == 2) {
            int j = (bs[1] & 255) << 8 | bs[0] & 255;
            if (j == 35615) {
                bl = true;
            }
        }

        if (i != 0) {
            stream.unread(bs, 0, i);
        }

        return bl;
    }


}
