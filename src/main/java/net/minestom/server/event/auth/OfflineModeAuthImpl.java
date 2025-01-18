package net.minestom.server.event.auth;

record OfflineModeAuthImpl() implements OfflineModeAuth {

    static final class OfflineModeBuilderImpl implements OfflineModeAuth.OfflineModeBuilder {
        @Override
        public OfflineModeAuth buildOffline() {
            return new OfflineModeAuthImpl();
        }
    }

}
