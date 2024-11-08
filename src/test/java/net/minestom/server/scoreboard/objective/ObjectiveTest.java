package net.minestom.server.scoreboard.objective;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.scoreboard.score.criteria.ObjectiveCriteria;
import net.minestom.server.scoreboard.score.criteria.RenderType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveTest {

    @Test
    void testObjectiveCreationWithInvalidName() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> Objective.builder().name("").build(),
                "The name cannot be empty"
        );
    }

    @Test
    void testObjectiveCreation() {
        Objective objective = Objective.builder()
                .name("dummy")
                .displayName(Component.text("dummy"))
                .criteria(ObjectiveCriteria.of("dummyCriteria"))
                .renderType(RenderType.HEARTS)
                .build();
        assertNotNull(objective);
        assertEquals("dummy", objective.name());
        assertNotNull(objective.displayName());
        assertNotEquals(Component.empty(), objective.displayName());
        assertNotEquals(RenderType.INTEGER, objective.renderType());

        assertNotNull(objective.criteria());

        ObjectiveCriteria criteria = objective.criteria();
        assertEquals("dummyCriteria", criteria.name());
    }

    @Test
    void testObjectiveDataUpdate() {
        Objective objective = Objective.builder().name("dummy").build();

        assertNull(objective.displayName());
        assertEquals(RenderType.INTEGER, objective.renderType());
        assertNull(objective.numberFormat());

        objective.updateDisplayName(Component.text("dummy"));
        assertNotNull(objective.displayName());
        assertEquals("dummy", PlainTextComponentSerializer.plainText().serialize(objective.displayName()));

        objective.updateRenderType(RenderType.HEARTS);
        assertEquals(RenderType.HEARTS, objective.renderType());
    }

    @Test
    void testObjectiveBuilderCopy() {
        Objective objective = Objective.builder()
                .name("Dummy")
                .renderType(RenderType.HEARTS)
                .build();

        Objective updatedObjective = Objective.builder(objective)
                .name("Updated")
                .renderType(RenderType.INTEGER)
                .criteria(ObjectiveCriteria.of("dummyCriteria"))
                .build();

        assertNotNull(objective);
        assertNotNull(updatedObjective);

        assertNotNull(objective.name(), updatedObjective.name());
        assertNotEquals(objective.renderType(), updatedObjective.renderType());
        assertNotNull(updatedObjective.criteria());
    }
}
