package net.minestom.server.entity;

import net.minestom.server.instance.Instance;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.potion.TimedPotion;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
public class EntityEffectIntegrationTest {

    private static final Potion TEST_POTION = new Potion(PotionEffect.SPEED, (byte) 1, 1000);

    @Test
    void testEffectAdd(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        player.addEffect(TEST_POTION);

        assertTrue(player.hasEffects());
        assertTrue(player.hasEffect(TEST_POTION.effect()));

        assertFalse(player.getActiveEffects().isEmpty());

        env.destroyInstance(instance, true);
    }

    @Test
    void testEffectRemove(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        player.addEffect(TEST_POTION);

        assertTrue(player.hasEffects());

        player.removeEffect(PotionEffect.ABSORPTION);
        assertTrue(player.hasEffects());

        player.removeEffect(TEST_POTION.effect());
        assertFalse(player.hasEffects());

        env.destroyInstance(instance, true);
    }

    @Test
    void testClearEffectFromPlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        player.addEffect(TEST_POTION);

        assertTrue(player.hasEffects());

        player.clearEffects();
        assertFalse(player.hasEffects());

        env.destroyInstance(instance, true);
    }

    @Test
    void testPotionGetWhichIsNotPresent(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        TimedPotion potion = player.getEffect(PotionEffect.DARKNESS);
        assertNull(potion);

        env.destroyInstance(instance, true);
    }

    @Test
    void testPotionGet(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        player.addEffect(TEST_POTION);

        TimedPotion potion = player.getEffect(PotionEffect.SPEED);
        assertNotNull(potion);

        env.destroyInstance(instance, true);
    }

    @Test
    void testPotionLevelGet(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        player.addEffect(new Potion(PotionEffect.ABSORPTION, (byte) 2, 1000));

        int level = player.getEffectLevel(PotionEffect.ABSORPTION);
        assertEquals(2, level);

        env.destroyInstance(instance, true);
    }

    @DisplayName("Test negative potion level get, when the potion is not present")
    @Test
    void testNegativePotionLevelGet(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);

        assertFalse(player.hasEffects());

        int level = player.getEffectLevel(PotionEffect.DARKNESS);
        assertEquals(-1, level);

        env.destroyInstance(instance, true);
    }
}
