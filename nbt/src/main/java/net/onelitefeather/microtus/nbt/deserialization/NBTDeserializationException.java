package net.onelitefeather.microtus.nbt.deserialization;

public final class NBTDeserializationException extends RuntimeException {
    public NBTDeserializationException(int position, String error, Object... args) {
        super("Deserialization exception at position %s: %s".formatted(position, error.formatted(args)));
    }

    public NBTDeserializationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
