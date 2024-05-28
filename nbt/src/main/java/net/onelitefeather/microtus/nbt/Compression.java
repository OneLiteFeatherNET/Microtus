package net.onelitefeather.microtus.nbt;

public sealed interface Compression {

    int bufferSize();

    default boolean isBufferSizeSet() {
        return bufferSize() >= 0;
    }

    static Compression none() {
        return CompressionImpl.NONE;
    }

    static Compression gzip() {
        return CompressionImpl.GZIP;
    }

    static Compression zlib() {
        return CompressionImpl.ZLIB;
    }

    enum CompressionImpl implements Compression {
        NONE(-1),
        GZIP(-1),
        ZLIB(-1),
        ;
        private final int bufferSize;

        CompressionImpl(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        @Override
        public int bufferSize() {
            return this.bufferSize;
        }
    }
}
