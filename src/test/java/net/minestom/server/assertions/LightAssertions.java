package net.minestom.server.assertions;

import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public final class LightAssertions {

    public static void assertLightInstance(@NotNull Instance instance, @NotNull Map<Vec, Integer> expectedLights) {
        List<String> errors = new ArrayList<>();
        for (var entry : expectedLights.entrySet()) {
            final Integer expected = entry.getValue();
            final Vec pos = entry.getKey();

            final byte light = lightVal(instance, pos);

            if (light != expected) {
                String errorLine = String.format("Expected %d at [%d,%d,%d] but got %d", expected, pos.blockX(), pos.blockY(), pos.blockZ(), light);
                System.err.println();
                errors.add(errorLine);
            }
        }
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : errors) {
                sb.append(s).append("\n");
            }
            System.err.println(sb);
            fail();
        }
    }

    static byte lightVal(@NotNull Instance instance, @NotNull Vec pos) {
        final Vec modPos = new Vec(((pos.blockX() % 16) + 16) % 16, ((pos.blockY() % 16) + 16) % 16, ((pos.blockZ() % 16) + 16) % 16);
        Chunk chunk = instance.getChunkAt(pos.blockX(), pos.blockZ());
        return (byte) chunk.getSectionAt(pos.blockY()).blockLight().getLevel(modPos.blockX(), modPos.blockY(), modPos.blockZ());
    }

    private LightAssertions() {}
}
