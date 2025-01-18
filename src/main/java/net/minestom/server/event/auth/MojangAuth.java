package net.minestom.server.event.auth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import java.security.KeyPair;

/**
 * Represents the Mojang authentication.
 * <p>
 * This interface is used to determine if the player is using Mojang authentication.
 * </p>
 * Use {@link Auth#mojangBuilder()} to create a new instance of this class.
 *
 * @version 1.0.0
 * @since 1.6.0
 * @author TheMeinerLP
 */
public sealed interface MojangAuth extends Auth permits MojangAuthImpl {

    /**
     * Gets the key pair for the Mojang authentication.
     *
     * @return the key pair
     */
    @NotNull KeyPair keyPair();

    byte @Nullable [] digestData(@NotNull String data, byte @NotNull [] sharedSecret);

    byte[] getNonce(byte[] bytes);

    Cipher getCipher(int mode,byte[] sharedSecret);

    /**
    * Represents the Mojang authentication.
    * <p>
    * This interface is used to determine if the player is using Mojang authentication.
    * </p>
    * Use {@link Auth#mojangBuilder()} to create a new instance of this class.
    *
    * @version 1.0.0
    * @since 1.6.0
    * @author TheMeinerLP
    */
    sealed interface MojangAuthBuilder extends AuthBuilder permits MojangAuthImpl.MojangAuthBuilderImpl {

        /**
         * Sets the key pair for the Mojang authentication.
         *
         * @param keyPair the key pair
         * @return this builder
         */
        MojangAuthBuilder keyPair(@NotNull KeyPair keyPair);

        /**
         * Generates a new key pair for the Mojang authentication.
         *
         * @return this builder
         */
        MojangAuthBuilder generateKeyPair();

        /**
         * Builds the Mojang authentication.
         *
         * @return the Mojang authentication
         * @throws IllegalStateException if the key pair is not set
         */
        MojangAuth build();
    }
}
