package net.minestom.server.item;

import net.minestom.server.MinecraftServer;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MicrotusExtension.class)
class MaterialReadTest {

    @Test
    void loadAllMaterials(@NotNull Env env) {
        // Materials are lazy loaded now so this is a sanity check that they all load
        for (Material material : Material.values()) {
            // Just loading the material should be enough to test that it exists
            assertNotNull(material.prototype());
        }
    }

}
