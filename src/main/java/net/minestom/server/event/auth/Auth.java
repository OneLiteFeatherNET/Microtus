package net.minestom.server.event.auth;

/**
 * Represents an authentication method that allows developers to decide how to authenticate players.
 *
 * @since 1.6.0
 * @author TheMeinerLP
 * @version 1.0.0
 */
public sealed interface Auth permits BungeeCordAuth, BungeeGuardAuth, MojangAuth, OfflineModeAuth, VelocityAuth {

    /**
     * Creates a new offline mode authentication builder.
     *
     * @return a new offline mode authentication builder
     */
    static OfflineModeAuth.OfflineModeBuilder offlineModeBuilder() {
        return new OfflineModeAuthImpl.OfflineModeBuilderImpl();
    }

    /**
     * Creates a new velocity authentication builder.
     *
     * @return a new velocity authentication builder
     */
    static VelocityAuth.VelocityAuthBuilder velocityBuilder() {
        return new VelocityAuthImpl.VelocityAuthBuilderImpl();
    }

    /**
     * Creates a new mojang authentication builder.
     *
     * @return a new mojang authentication builder
     */
    static MojangAuth.MojangAuthBuilder mojangBuilder() {
        return new MojangAuthImpl.MojangAuthBuilderImpl();
    }

    /**
     * Creates a new bungeecord authentication builder.
     *
     * @return a new bungeecord authentication builder
     */
    static BungeeCordAuth.BungeeCordAuthBuilder bungeeCordBuilder() {
        return new BungeeCordAuthImpl.BungeeCordAuthBuilderImpl();
    }

    /**
     * Creates a new bungeeguard authentication builder.
     *
     * @return a new bungeeguard authentication builder
     */
    static BungeeGuardAuth.BungeeGuardAuthBuilder bungeeGuardBuilder() {
        return new BungeeGuardAuthImpl.BungeeGuardAuthBuilderImpl();
    }


    /**
     * Represents an base structure for authentication builders.
     *
     * @since 1.6.0
     * @version 1.0.0
     * @author TheMeinerLP
     */
    sealed interface AuthBuilder permits BungeeCordAuth.BungeeCordAuthBuilder, BungeeGuardAuth.BungeeGuardAuthBuilder, MojangAuth.MojangAuthBuilder, OfflineModeAuth.OfflineModeBuilder, VelocityAuth.VelocityAuthBuilder {
    }


}
