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
import org.jetbrains.annotations.NotNull;

public final class DynamicRegistries implements Registries {
    private final DynamicRegistry<BinaryTagSerializer<? extends LevelBasedValue>> enchantmentLevelBasedValues;
    private final DynamicRegistry<BinaryTagSerializer<? extends ValueEffect>> enchantmentValueEffects;
    private final DynamicRegistry<BinaryTagSerializer<? extends EntityEffect>> enchantmentEntityEffects;
    private final DynamicRegistry<BinaryTagSerializer<? extends LocationEffect>> enchantmentLocationEffects;
    private final DynamicRegistry<ChatType> chatType;
    private final DynamicRegistry<DimensionType> dimensionType;
    private final DynamicRegistry<Biome> biome;
    private final DynamicRegistry<DamageType> damageType;
    private final DynamicRegistry<TrimMaterial> trimMaterial;
    private final DynamicRegistry<TrimPattern> trimPattern;
    private final DynamicRegistry<BannerPattern> bannerPattern;
    private final DynamicRegistry<WolfMeta.Variant> wolfVariant;
    private final DynamicRegistry<Enchantment> enchantment;
    private final DynamicRegistry<PaintingMeta.Variant> paintingVariant;
    private final DynamicRegistry<JukeboxSong> jukeboxSong;

    public DynamicRegistries() {
        // The order of initialization here is relevant, we must load the enchantment util registries before the vanilla data is loaded.
        this.enchantmentLevelBasedValues = LevelBasedValue.createDefaultRegistry();
        this.enchantmentValueEffects = ValueEffect.createDefaultRegistry();
        this.enchantmentEntityEffects = EntityEffect.createDefaultRegistry();
        this.enchantmentLocationEffects = LocationEffect.createDefaultRegistry();
        this.chatType = ChatType.createDefaultRegistry();
        this.dimensionType = DimensionType.createDefaultRegistry();
        this.biome = Biome.createDefaultRegistry();
        this.damageType = DamageType.createDefaultRegistry();
        this.trimMaterial = TrimMaterial.createDefaultRegistry();
        this.trimPattern = TrimPattern.createDefaultRegistry();
        this.bannerPattern = BannerPattern.createDefaultRegistry();
        this.wolfVariant = WolfMeta.Variant.createDefaultRegistry();
        this.enchantment = Enchantment.createDefaultRegistry(this);
        this.paintingVariant = PaintingMeta.Variant.createDefaultRegistry();
        this.jukeboxSong = JukeboxSong.createDefaultRegistry();
    }

    @Override
    public @NotNull DynamicRegistry<BinaryTagSerializer<? extends LevelBasedValue>> enchantmentLevelBasedValues() {
        return enchantmentLevelBasedValues;
    }

    @Override
    public @NotNull DynamicRegistry<BinaryTagSerializer<? extends ValueEffect>> enchantmentValueEffects() {
        return enchantmentValueEffects;
    }

    @Override
    public @NotNull DynamicRegistry<BinaryTagSerializer<? extends EntityEffect>> enchantmentEntityEffects() {
        return enchantmentEntityEffects;
    }

    @Override
    public @NotNull DynamicRegistry<BinaryTagSerializer<? extends LocationEffect>> enchantmentLocationEffects() {
        return enchantmentLocationEffects;
    }

    @Override
    public @NotNull DynamicRegistry<ChatType> chatType() {
        return chatType;
    }

    @Override
    public @NotNull DynamicRegistry<DimensionType> dimensionType() {
        return dimensionType;
    }

    @Override
    public @NotNull DynamicRegistry<Biome> biome() {
        return biome;
    }

    @Override
    public @NotNull DynamicRegistry<DamageType> damageType() {
        return damageType;
    }

    @Override
    public @NotNull DynamicRegistry<TrimMaterial> trimMaterial() {
        return trimMaterial;
    }

    @Override
    public @NotNull DynamicRegistry<TrimPattern> trimPattern() {
        return trimPattern;
    }

    @Override
    public @NotNull DynamicRegistry<BannerPattern> bannerPattern() {
        return bannerPattern;
    }

    @Override
    public @NotNull DynamicRegistry<WolfMeta.Variant> wolfVariant() {
        return wolfVariant;
    }

    @Override
    public @NotNull DynamicRegistry<Enchantment> enchantment() {
        return enchantment;
    }

    @Override
    public @NotNull DynamicRegistry<PaintingMeta.Variant> paintingVariant() {
        return paintingVariant;
    }

    @Override
    public @NotNull DynamicRegistry<JukeboxSong> jukeboxSong() {
        return jukeboxSong;
    }
}
