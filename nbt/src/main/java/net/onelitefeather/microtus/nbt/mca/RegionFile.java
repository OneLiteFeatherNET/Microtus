package net.onelitefeather.microtus.nbt.mca;

import net.onelitefeather.microtus.nbt.Mode;
import net.onelitefeather.microtus.nbt.deserialization.Deserializer;
import net.onelitefeather.microtus.nbt.tree.Tag;
import net.onelitefeather.microtus.nbt.tree.TagCompound;
import net.onelitefeather.microtus.nbt.tree.TagRootCompound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

public record RegionFile(Path mcaFile, FileChannel fileChannel) {
    public RegionFile(Path path) throws IOException {
        this(path, FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC));
    }
    public RegionFile(Path basePath, int x, int z) throws IOException {
        this(basePath.resolve("region").resolve("r."+ x + '.' + z + ".mca"));
    }

    public void load() {
        try {
            long fileLength = fileChannel.size();
            long totalSectors = roundToSectors(fileLength);
            for (long i = 2, maxSector = Math.min((long)(Integer.MAX_VALUE >>> 8), totalSectors); i < maxSector; ++i) { // first two sectors are header, skip
                int chunkDataLength = this.getLength(i);
                Tag compound = this.attemptRead(i, chunkDataLength, fileLength);
                if (compound instanceof TagCompound tc) {
                    tc.keys().forEach(System.out::println);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getLength(long sector) throws IOException {
        ByteBuffer length = ByteBuffer.allocate(4);
        if (4 != this.fileChannel.read(length, sector * 4096L)) {
            return -1;
        }

        return length.getInt(0);
    }

    private static long roundToSectors(long bytes) {
        long sectors = bytes >>> 12; // 4096 = 2^12
        long remainingBytes = bytes & 4095;
        long sign = -remainingBytes; // sign is 1 if nonzero
        return sectors + (sign >>> 63);
    }
    private Tag attemptRead(long sector, int chunkDataLength, long fileLength) throws IOException {
        try {
            if (chunkDataLength < 0) {
                return null;
            }

            long offset = sector * 4096L + 4L; // offset for chunk data

            if ((offset + chunkDataLength) > fileLength) {
                return null;
            }

            ByteBuffer chunkData = ByteBuffer.allocate(chunkDataLength);
            if (chunkDataLength != this.fileChannel.read(chunkData, offset)) {
                return null;
            }

            ((java.nio.Buffer) chunkData).flip();

            var r = Deserializer.deserialize(new PushbackInputStream(new ByteArrayInputStream(chunkData.array(), chunkData.position(), chunkDataLength - chunkData.position()), 2), Mode.file());
            return r.deserializeBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
