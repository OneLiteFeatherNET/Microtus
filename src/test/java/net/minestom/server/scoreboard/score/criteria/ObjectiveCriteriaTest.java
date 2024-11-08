package net.minestom.server.scoreboard.score.criteria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCriteriaTest {

    @Test
    void testObjectiveCriteriaWithDefaultRenderType() {
        ObjectiveCriteria criteria = ObjectiveCriteria.of("dummy");
        assertNotNull(criteria);
        assertEquals("dummy", criteria.name());
        assertNotEquals(RenderType.HEARTS, criteria.renderType());
        assertEquals(RenderType.INTEGER, criteria.renderType());
    }

    @Test
    void testObjectiveCriteria() {
        ObjectiveCriteria criteria = ObjectiveCriteria.of("dummy", RenderType.HEARTS);
        assertNotNull(criteria);
        assertEquals("dummy", criteria.name());
        assertEquals(RenderType.HEARTS, criteria.renderType());
    }
}
