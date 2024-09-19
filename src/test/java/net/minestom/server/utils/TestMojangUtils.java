package net.minestom.server.utils;

import net.minestom.server.utils.mojang.MojangUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TestMojangUtils {
    private final UUID JEB_UUID = UUID.fromString("853c80ef-3c37-49fd-aa49-938b674adae6");

    @Disabled
    @Test
    void testValidNameWorks() {
        var result = MojangUtils.fromUsername("jeb_");
        assertNotNull(result);
        assertEquals("jeb_", result.get("name").getAsString());
    }

    @Disabled
    @Test
    void testInvalidNameReturnsNull() {
        var result = MojangUtils.fromUsername("jfdsa84vvcxadubasdfcvn"); // Longer than 16, always invalid
        assertNull(result);
    }

    @Disabled
    @Test
    void testValidUuidWorks() {
        var result = MojangUtils.fromUuid(JEB_UUID.toString());
        assertNotNull(result);
        assertEquals("jeb_", result.get("name").getAsString());
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    }

    @Disabled
    @Test
    void testInvalidUuidReturnsNull() {
        var result = MojangUtils.fromUuid("853c80ef3c3749fdaa49938b674adae6a"); // Longer than 32, always invalid
        assertNull(result);
    }

    @Disabled
    @Test
    void testNonExistentUuidReturnsNull() {
        var result = MojangUtils.fromUuid("00000000-0000-0000-0000-000000000000");
        assertNull(result);
    }

    @Disabled
    @Test
    void testValidUUIDWorks() {
        var result = MojangUtils.fromUuid(JEB_UUID);
        assertNotNull(result);
        assertEquals("jeb_", result.get("name").getAsString());
        assertEquals("853c80ef3c3749fdaa49938b674adae6", result.get("id").getAsString());
    }

    @Disabled
    @Test
    void testGetValidNameWorks() throws IOException {
        assertEquals(JEB_UUID, MojangUtils.getUUID("jeb_"));
    }

    @Disabled
    @Test
    void testGetValidUUIDWorks() throws IOException {
        assertEquals("jeb_", MojangUtils.getUsername(JEB_UUID));
    }

    @Disabled
    @Test
    void testGetInvalidNameThrows() {
        assertThrows(IOException.class, () -> MojangUtils.getUUID("a")); // Too short
    }
}
