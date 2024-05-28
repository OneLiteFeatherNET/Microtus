package net.onelitefeather.microtus.nbt;

public sealed interface Mode {

    static Mode network() {
        return ModeImpl.NETWORK;
    }

    static Mode file() {
        return ModeImpl.FILE;
    }

    enum ModeImpl implements Mode {
        FILE,
        NETWORK
    }
}
