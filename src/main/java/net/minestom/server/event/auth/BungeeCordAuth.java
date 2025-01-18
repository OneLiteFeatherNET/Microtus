package net.minestom.server.event.auth;

/**
 * Represents the BungeeCord authentication.
 * <p>
 * This interface is used to determine if the player is using BungeeCord authentication.
 * </p>
 * Use {@link Auth#bungeeCordBuilder()} to create a new instance of this class.
 *
 * @version 1.0.0
 * @since 1.6.0
 * @author TheMeinerLP
 */
public sealed interface BungeeCordAuth extends Auth permits BungeeCordAuthImpl {

    sealed interface BungeeCordAuthBuilder extends AuthBuilder permits BungeeCordAuthImpl.BungeeCordAuthBuilderImpl {

        /**
         * Builds the BungeeCord authentication.
         *
         * @return the BungeeCord authentication
         */
        BungeeCordAuth build();

    }

}
