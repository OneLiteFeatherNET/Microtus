package net.minestom.server.instance.block.predicate;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.Block;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTypeFilterTest {

    static {
        MinecraftServer.init();
    }

    @Test
    void testBlockExact() {
        var filter = new BlockTypeFilter.Blocks(Block.STONE);
        var block = Block.STONE;
        assertTrue(filter.test(block));
    }

    @Test
    void testBlockExactMulti() {
        var filter = new BlockTypeFilter.Blocks(Block.STONE, Block.STONE_STAIRS);
        var block = Block.STONE_STAIRS;
        assertTrue(filter.test(block));
    }

    @Test
    void testBlockExactMultiMissing() {
        var filter = new BlockTypeFilter.Blocks(Block.STONE, Block.STONE_STAIRS);
        var block = Block.DIRT;
        assertFalse(filter.test(block));
    }

    @Test
    void testBlockExactDifferentPropertyA() {
        var filter = new BlockTypeFilter.Blocks(Block.STONE_STAIRS);
        var block = Block.STONE_STAIRS.withProperty("shape", "inner_left");
        assertTrue(filter.test(block));
    }

    @Test
    void testBlockExactDifferentPropertyB() {
        var filter = new BlockTypeFilter.Blocks(Block.STONE_STAIRS.withProperty("shape", "inner_left"));
        var block = Block.STONE_STAIRS;
        assertTrue(filter.test(block));
    }

    @Test
    void testTag() {
        var filter = new BlockTypeFilter.Tag("minecraft:doors");
        var block = Block.OAK_DOOR;
        assertTrue(filter.test(block));
    }

    @Test
    void testTagDifferentProperty() {
        var filter = new BlockTypeFilter.Tag("minecraft:doors");
        var block = Block.OAK_DOOR.withProperty("half", "upper");
        assertTrue(filter.test(block));
    }

    @Test
    void testTagMissing() {
        var filter = new BlockTypeFilter.Tag("minecraft:doors");
        var block = Block.STONE;
        assertFalse(filter.test(block));
    }

    @Test
    void testTagUnknown() {
        assertThrows(NullPointerException.class, () -> new BlockTypeFilter.Tag("minecraft:not_a_tag"));
    }
}
