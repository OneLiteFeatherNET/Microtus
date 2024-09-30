package net.minestom.server.utils.async;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public final class NamedThreadFactory implements ThreadFactory {
    private final String name;
    private final boolean virtual;
    private final boolean daemon;

    NamedThreadFactory(String name) {
        this.name = name;
        this.virtual = false;
        this.daemon = true;
    }

    public NamedThreadFactory(String name, boolean virtual, boolean daemon) {
        this.name = name;
        this.virtual = virtual;
        this.daemon = daemon;
    }

    public static NamedThreadFactory of(String name) {
        return new NamedThreadFactory(name);
    }

    public static NamedThreadFactory of(String name, boolean virtual) {
        return new NamedThreadFactory(name, virtual, true);
    }

    public static NamedThreadFactory of(String name, boolean virtual, boolean daemon) {
        return new NamedThreadFactory(name, virtual, daemon);
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        if (virtual) return Thread.ofVirtual().name(this.name, 0).unstarted(r);
        return Thread. ofPlatform().name(this.name, 0).unstarted(r);
    }
}
