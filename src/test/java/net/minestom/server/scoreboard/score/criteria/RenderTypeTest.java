package net.minestom.server.scoreboard.score.criteria;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RenderTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"hearts", "integer"})
    void testGetRenderType(String name) {
        RenderType[] values = RenderType.values();
        RenderType parsedType = Arrays.stream(values)
                .filter(type -> type.getId().equals(name))
                .findFirst()
                .orElse(null);
        assertNotNull(parsedType);
        assertEquals(name, parsedType.getId());
    }
}
