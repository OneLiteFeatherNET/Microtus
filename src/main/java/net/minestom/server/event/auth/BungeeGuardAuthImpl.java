package net.minestom.server.event.auth;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

record BungeeGuardAuthImpl(Set<String> tokens) implements BungeeGuardAuth {

    static final class BungeeGuardAuthBuilderImpl implements BungeeGuardAuth.BungeeGuardAuthBuilder {

        private Set<String> tokens;

        @Override
        public @NotNull BungeeGuardAuthBuilder tokens(@NotNull Set<String> tokens) {
            this.tokens = tokens;
            return this;
        }

        @Override
        public @NotNull BungeeGuardAuth build() {
            if (tokens == null) {
                throw new IllegalStateException("Tokens cannot be null");
            }
            if (tokens.isEmpty()) {
                throw new IllegalStateException("Tokens cannot be empty");
            }
            return new BungeeGuardAuthImpl(tokens);
        }
    }
}
