package net.minestom.server.event.auth;

import net.minestom.server.event.Event;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player starts the authentication process.
 *
 * @version 1.0.0
 * @since 1.6.0
 * @author TheMeinerLP
 * @see Auth
 */
public final class AuthEvent implements Event {

    private final PlayerConnection playerConnection;
    private Auth auth;
    private final boolean isSocketConnection;

    private AuthEvent(PlayerConnection playerConnection, Auth auth) {
        this.playerConnection = playerConnection;
        this.auth = auth;
        this.isSocketConnection = playerConnection instanceof PlayerSocketConnection;
    }

    private AuthEvent(PlayerConnection playerConnection) {
        this(playerConnection, null);
    }

    public PlayerConnection getPlayerConnection() {
        return playerConnection;
    }

    public boolean isSocketConnection() {
        return isSocketConnection;
    }

    public @Nullable Auth getAuth() {
        return auth;
    }

    public void setAuth(@NotNull Auth auth) {
        this.auth = auth;
    }

    public static AuthEvent of(@NotNull PlayerConnection playerConnection) {
        return new AuthEvent(playerConnection);
    }
}
