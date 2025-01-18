package net.minestom.server.event.player;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called before the player initialization, it can be used to kick the player before any connection
 * or to change his final username/uuid.
 */
public class ModernPlayerPreLoginEvent implements PlayerEvent {

    private final Player player;

    private String username;

    public ModernPlayerPreLoginEvent(@NotNull Player player) {
        this.player = player;
        this.username = player.getUsername();
    }

    /**
     * Gets the player username.
     *
     * @return the player username
     */
    @NotNull
    public String getUsername() {
        return username;
    }

    /**
     * Changes the player username.
     *
     * @param username the new player username
     */
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
