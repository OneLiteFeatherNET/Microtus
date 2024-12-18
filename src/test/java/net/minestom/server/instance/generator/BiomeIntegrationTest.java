package net.minestom.server.instance.generator;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.generator.GeneratorImpl.GenSection;
import net.minestom.server.world.biome.Biome;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MicrotusExtension.class)
class BiomeIntegrationTest {

    private static int plainsId, badLandsId;

    @BeforeAll
    static void prepareTest(Env env) {
        plainsId = env.process().biome().getId(Biome.PLAINS);
        badLandsId = env.process().biome().getId(Biome.BADLANDS);
    }

    @Test
    void chunkBiomeSet(Env env) {
        final int minSection = -1;
        final int maxSection = 5;
        final int chunkX = 3;
        final int chunkZ = -2;
        final int sectionCount = maxSection - minSection;
        GenSection[] sections = new GenSection[sectionCount];
        Arrays.setAll(sections, i -> new GenSection());
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        Generator generator = unit -> {
            var modifier = unit.modifier();
            modifier.setBiome(48, -16, -32, Biome.BADLANDS);
            modifier.setBiome(48 + 8, 0, -32, Biome.BADLANDS);
        };
        generator.generate(chunkUnits);

        // Reminder because I (matt) forgot: biome palettes are 4x4x4 sections, so x=2 is really x=8 in the chunk.
        assertEquals(badLandsId, sections[0].biomes().get(0, 0, 0));
        assertEquals(plainsId, sections[1].biomes().get(1, 0, 0));
        assertEquals(badLandsId, sections[1].biomes().get(2, 0, 0));
    }

    @Test
    void chunkBiomeFill(Env env) {
        final int minSection = -1;
        final int maxSection = 5;
        final int chunkX = 3;
        final int chunkZ = -2;
        final int sectionCount = maxSection - minSection;
        GenSection[] sections = new GenSection[sectionCount];
        Arrays.setAll(sections, i -> new GenSection());
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        Generator generator = chunk -> {
            var modifier = chunk.modifier();
            modifier.fillBiome(Biome.PLAINS);
        };
        generator.generate(chunkUnits);
        for (var section : sections) {
            section.biomes().getAll((x, y, z, value) ->
                    assertEquals(plainsId, value));
        }
    }
}
