package net.minestom.server.scoreboard;

import net.kyori.adventure.text.TranslatableComponent;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CollisionRuleTest {

    @Test
    void testInvalidCollisionFromIdentifier() {
        assertNull(TeamsPacket.CollisionRule.fromIdentifier("hideForAll"));
    }

    @ParameterizedTest(name = "Test Collision fromIdentifier with {0}")
    @ValueSource(strings = {"always", "never", "pushOtherTeams", "pushOwnTeam"})
    void testCollisionFromIdentifier(@NotNull String identifier) {
        TeamsPacket.CollisionRule fetchedRule = TeamsPacket.CollisionRule.fromIdentifier(identifier);
        assertNotNull(fetchedRule);
        assertEquals(identifier, fetchedRule.getIdentifier());

        assertInstanceOf(TranslatableComponent.class, fetchedRule.getDisplayName());

        TranslatableComponent component = (TranslatableComponent) fetchedRule.getDisplayName();
        assertTrue(component.key().contains(identifier));
    }
}
