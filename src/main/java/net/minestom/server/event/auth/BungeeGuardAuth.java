package net.minestom.server.event.auth;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public sealed interface BungeeGuardAuth extends Auth permits BungeeGuardAuthImpl {

    /**
     * Gets the BungeeGuard tokens.
     *
     * @return the BungeeGuard token
     */
    @NotNull Set<String> tokens();

    sealed interface BungeeGuardAuthBuilder extends AuthBuilder permits BungeeGuardAuthImpl.BungeeGuardAuthBuilderImpl {
        /**
         * Sets the BungeeGuard tokens.
         *
         * @param tokens the BungeeGuard tokens
         * @return this builder
         */
        @NotNull BungeeGuardAuthBuilder tokens(@NotNull Set<String> tokens);

        /**
         * Builds the BungeeGuard authentication.
         *
         * @return the BungeeGuard authentication
         * @throws IllegalStateException if the tokens are null or empty
         */
        @NotNull BungeeGuardAuth build();
    }
}
