package net.minestom.server.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a static protocol object.
 * Used for objects which are not dynamic and are known at compile time.
 *
 * @version 1.0.0
 * @since 1.0.0
 * @author themeinerlp
 */
public interface StaticProtocolObject extends ProtocolObject, Keyed {

    /**
     * Gets the namespace ID of this object.
     *
     * @return the namespace ID
     */
    @Contract(pure = true)
    @NotNull Key namespace();

    /**
     * Gets the name of this object.
     *
     * @return the name
     */
    @Contract(pure = true)
    default @NotNull String name() {
        return namespace().asString();
    }

    /**
     * Gets the key of this object.
     *
     * @return the key
     */
    @Override
    @Contract(pure = true)
    default @NotNull Key key() {
        return namespace();
    }

    /**
     * Gets the ID of this object.
     *
     * @return the ID
     */
    @Contract(pure = true)
    int id();
}
