package net.minestom.server.entity;

import net.minestom.server.color.Color;
import net.minestom.server.entity.metadata.other.AreaEffectCloudMeta;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.particle.Particle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AreaEffectCloudTest {
    @Test
    void createWithDustParticle() {
        int colour = 0x5505FF01;

        int b = (colour & 0x000000FF);
        int g = (colour & 0x0000FF00) >> 8;
        int r = (colour & 0x00FF0000) >> 16;

        float size = 0.1f;

        Particle particle = Particle.DUST.withProperties(new Color(r, g, b), size);

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.Dust gotData = (Particle.Dust) gotParticle;
        assertNotNull(gotData);
        assert gotData.color().red() == r;
        assert gotData.color().green() == g;
        assert gotData.color().blue() == b;
        assert gotData.scale() == size;
    }

    @Test
    void createWithDustTransition() {
        int colour = 0xFF05FF01;
        int colourAfter = 0xFF05FF01;

        int b = (colour & 0x000000FF);
        int g = (colour & 0x0000FF00) >> 8;
        int r = (colour & 0x00FF0000) >> 16;

        int b2 = (colourAfter & 0x000000FF);
        int g2 = (colourAfter & 0x0000FF00) >> 8;
        int r2 = (colourAfter & 0x00FF0000) >> 16;

        float size = 0.1f;

        Particle particle = Particle.DUST_COLOR_TRANSITION.withProperties(new Color(r, g, b), new Color(r2, g2, b2), size);

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.DustColorTransition gotData = (Particle.DustColorTransition) gotParticle;
        assertNotNull(gotData);
        assert gotData.color().red() == r;
        assert gotData.color().green() == g;
        assert gotData.color().blue() == b;
        assert gotData.scale() == size;
        assert gotData.transitionColor().red() == r2;
        assert gotData.transitionColor().green() == g2;
        assert gotData.transitionColor().blue() == b2;
    }

    @Test
    void createWithBlockParticle() {
        Block block = Block.GRASS_BLOCK;
        Particle particle = Particle.BLOCK.withBlock(block);

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.Block gotBlock = (Particle.Block) gotParticle;
        assertEquals(block, gotBlock.block());
    }

    @Test
    void createWithBlockMarkerParticle() {
        Block block = Block.GRASS_BLOCK;
        Particle particle = Particle.BLOCK_MARKER.withBlock(block);

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.BlockMarker gotBlock = (Particle.BlockMarker) gotParticle;
        assertEquals(block, gotBlock.block());
    }

    @Test
    void createWithItemParticle() {
        Particle particle = Particle.ITEM.withItem(ItemStack.of(Material.ACACIA_LOG));

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.Item gotBlock = (Particle.Item) gotParticle;
        assertEquals(Material.ACACIA_LOG, gotBlock.item().material());
    }

    @Test
    void createWithSculkChargeParticle() {
        Particle particle = Particle.SCULK_CHARGE.withRoll(3);

        Entity entity = new Entity(EntityTypes.AREA_EFFECT_CLOUD);
        AreaEffectCloudMeta meta = (AreaEffectCloudMeta) entity.getEntityMeta();
        meta.setParticle(particle);

        var gotParticle = meta.getParticle();
        assert gotParticle == particle;

        Particle.SculkCharge gotBlock = (Particle.SculkCharge) gotParticle;
        assertEquals(3, gotBlock.roll());
    }
}
