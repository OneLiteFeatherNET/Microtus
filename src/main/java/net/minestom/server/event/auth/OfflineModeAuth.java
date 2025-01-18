package net.minestom.server.event.auth;

/**
 * Represents an offline mode authentication.
 * <p>
 *     This interface is used to determine if the player is using offline mode authentication.
 * </p>
 *
 * @version 1.0.0
 * @since 1.6.0
 * @author TheMeinerLP
 */
public sealed interface OfflineModeAuth extends Auth permits OfflineModeAuthImpl {

    /**
     * Represents an offline authentication builder.
     *
     * @since 1.6.0
     * @version 1.0.0
     * @author TheMeinerLP
     */
    sealed interface OfflineModeBuilder extends AuthBuilder permits OfflineModeAuthImpl.OfflineModeBuilderImpl {

        /**
         * Builds the offline mode authentication.
         *
         * @return the offline mode authentication
         */
        OfflineModeAuth buildOffline();

    }
}
