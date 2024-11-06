package net.minestom.server.scoreboard;

import net.kyori.adventure.text.TranslatableComponent;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NameTagVisibilityTest {

    @Test
    void testInvalidNameTagVisibilityFromIdentifier() {
        assertNull(TeamsPacket.NameTagVisibility.fromIdentifier("hideForAll"));
    }

    @ParameterizedTest(name = "Test fromIdentifier with {0}")
    @ValueSource(strings = {"always", "never", "hideForOtherTeams", "hideForOwnTeam"})
    void testNameTagVisibilityFromIdentifier(@NotNull String identifier) {
        TeamsPacket.NameTagVisibility fetchedVisibility = TeamsPacket.NameTagVisibility.fromIdentifier(identifier);
        assertNotNull(fetchedVisibility);
        assertEquals(identifier, fetchedVisibility.getIdentifier());

        assertInstanceOf(TranslatableComponent.class, fetchedVisibility.getDisplayName());

        TranslatableComponent component = (TranslatableComponent) fetchedVisibility.getDisplayName();
        assertTrue(component.key().contains(identifier));
    }
}
