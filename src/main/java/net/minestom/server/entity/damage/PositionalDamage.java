package net.minestom.server.entity.damage;

import net.kyori.adventure.key.Key;
import net.minestom.server.coordinate.Point;
import org.jetbrains.annotations.NotNull;

/**
 * Represents damage that is associated with a certain position.
 */
public class PositionalDamage extends Damage {

    public PositionalDamage(@NotNull Key type, @NotNull Point sourcePosition, float amount) {
        super(type, null, null, sourcePosition, amount);
    }

}