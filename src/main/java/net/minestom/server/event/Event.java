package net.minestom.server.event;

import net.minestom.server.MinecraftServer;

/**
 * Event which can be listened to by an {@link EventNode} using {@link EventNode#addListener(EventListener)}.
 * <p>
 * Called using {@link EventDispatcher#call(Event)}.
 */
public interface Event {

    default <T extends Event> T call() {
        MinecraftServer.getGlobalEventHandler().call(this);
        return (T) this;
    }

}
