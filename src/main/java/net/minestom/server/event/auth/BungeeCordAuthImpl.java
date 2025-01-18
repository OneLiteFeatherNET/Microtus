package net.minestom.server.event.auth;

record BungeeCordAuthImpl() implements BungeeCordAuth {

    static final class BungeeCordAuthBuilderImpl implements BungeeCordAuth.BungeeCordAuthBuilder {

        @Override
        public BungeeCordAuth build() {
            return new BungeeCordAuthImpl();
        }

    }
}
