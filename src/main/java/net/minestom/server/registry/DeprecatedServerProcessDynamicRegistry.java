package net.minestom.server.registry;

import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.entity.metadata.animal.tameable.WolfMeta;
import net.minestom.server.entity.metadata.other.PaintingMeta;
import net.minestom.server.instance.block.banner.BannerPattern;
import net.minestom.server.instance.block.jukebox.JukeboxSong;
import net.minestom.server.item.armor.TrimMaterial;
import net.minestom.server.item.armor.TrimPattern;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.item.enchant.EntityEffect;
import net.minestom.server.item.enchant.LevelBasedValue;
import net.minestom.server.item.enchant.LocationEffect;
import net.minestom.server.item.enchant.ValueEffect;
import net.minestom.server.message.ChatType;
import net.minestom.server.utils.nbt.BinaryTagSerializer;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to represent the deprecated ServerProcess registry methods
 */
@Deprecated(forRemoval = true, since = "1.6.0")
@ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
public interface DeprecatedServerProcessDynamicRegistry extends Registries {
    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#chatType()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<ChatType> chatType();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#dimensionType()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<DimensionType> dimensionType();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#biome()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<Biome> biome();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#damageType()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<DamageType> damageType();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#trimMaterial()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<TrimMaterial> trimMaterial();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#trimPattern()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<TrimPattern> trimPattern();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#bannerPattern()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<BannerPattern> bannerPattern();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#wolfVariant()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<WolfMeta.Variant> wolfVariant();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#enchantment()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<Enchantment> enchantment();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#paintingVariant()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<PaintingMeta.Variant> paintingVariant();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#jukeboxSong()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<JukeboxSong> jukeboxSong();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#enchantmentLevelBasedValues()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<BinaryTagSerializer<? extends LevelBasedValue>> enchantmentLevelBasedValues();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#enchantmentValueEffects()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<BinaryTagSerializer<? extends ValueEffect>> enchantmentValueEffects();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#enchantmentEntityEffects()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<BinaryTagSerializer<? extends EntityEffect>> enchantmentEntityEffects();

    /**
     * @deprecated Use {@link net.minestom.server.ServerProcess#registries()} and {@link Registries#enchantmentLocationEffects()} ()}
     */
    @Deprecated(forRemoval = true, since = "1.6.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "1.7.0")
    @NotNull DynamicRegistry<BinaryTagSerializer<? extends LocationEffect>> enchantmentLocationEffects();

}
