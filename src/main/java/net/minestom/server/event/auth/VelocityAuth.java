package net.minestom.server.event.auth;

import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Velocity authentication.
 * <p>
 *     This interface is used to determine if the player is using Velocity authentication.
 * </p>
 *
 * @version 1.0.0
 * @since 1.6.0
 * @author TheMeinerLP
 */
public sealed interface VelocityAuth extends Auth permits VelocityAuthImpl {

    byte MAX_SUPPORTED_FORWARDING_VERSION = 4;
    String PLAYER_INFO_CHANNEL = "velocity:player_info";
    String MAC_ALGORITHM = "HmacSHA256";

    /**
     * Gets the secret for the Velocity authentication.
     *
     * @return the secret
     */
    @NotNull String secret();

    /**
     * Checks the integrity of the Velocity authentication.
     *
     * @param buffer the buffer to check
     * @return true if the authentication is valid, false otherwise
     */
    boolean checkIntegrity(@NotNull NetworkBuffer buffer);

    /**
     * Represents an Velocity authentication builder.
     *
     * @since 1.6.0
     * @version 1.0.0
     * @author TheMeinerLP
     */
    sealed interface VelocityAuthBuilder extends AuthBuilder permits VelocityAuthImpl.VelocityAuthBuilderImpl {

        /**
         * Sets the secret for the Velocity authentication.
         *
         * @param secret the secret
         * @return this builder
         */
        VelocityAuthBuilder secret(String secret);

        /**
         * Builds the Velocity authentication.
         *
         * @return the Velocity authentication
         * @throws IllegalStateException if the secret is not set
         */
        VelocityAuth build();

    }


}
