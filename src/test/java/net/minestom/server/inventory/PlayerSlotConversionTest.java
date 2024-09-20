package net.minestom.server.inventory;

import org.junit.jupiter.api.Test;

import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test conversion from packet slots to internal ones (used in events and inventory methods)
 */
class PlayerSlotConversionTest {

    @Test
    void hotbar() {
        // Convert 36-44 into 0-8
        for (int i = 0; i < 9; i++) {
            assertEquals(i, convertPlayerInventorySlot(i + 36, OFFSET));
        }
    }

    @Test
    void mainInventory() {
        // No conversion, slots should stay 9-35
        for (int i = 9; i < 9 * 4; i++) {
            assertEquals(i, convertPlayerInventorySlot(i, OFFSET));
        }
    }

    @Test
    void armor() {
        assertEquals(41, HELMET_SLOT);
        assertEquals(42, CHESTPLATE_SLOT);
        assertEquals(43, LEGGINGS_SLOT);
        assertEquals(44, BOOTS_SLOT);
        assertEquals(45, OFFHAND_SLOT);

        // Convert 5-8 & 45 into 41-45
        assertEquals(HELMET_SLOT, convertPlayerInventorySlot(5, OFFSET));
        assertEquals(CHESTPLATE_SLOT, convertPlayerInventorySlot(6, OFFSET));
        assertEquals(LEGGINGS_SLOT, convertPlayerInventorySlot(7, OFFSET));
        assertEquals(BOOTS_SLOT, convertPlayerInventorySlot(8, OFFSET));
        assertEquals(OFFHAND_SLOT, convertPlayerInventorySlot(45, OFFSET));
    }

    @Test
    void craft() {
        assertEquals(36, CRAFT_RESULT);
        assertEquals(37, CRAFT_SLOT_1);
        assertEquals(38, CRAFT_SLOT_2);
        assertEquals(39, CRAFT_SLOT_3);
        assertEquals(40, CRAFT_SLOT_4);

        // Convert 0-4 into 36-40
        assertEquals(CRAFT_RESULT, convertPlayerInventorySlot(0, OFFSET));
        assertEquals(CRAFT_SLOT_1, convertPlayerInventorySlot(1, OFFSET));
        assertEquals(CRAFT_SLOT_2, convertPlayerInventorySlot(2, OFFSET));
        assertEquals(CRAFT_SLOT_3, convertPlayerInventorySlot(3, OFFSET));
        assertEquals(CRAFT_SLOT_4, convertPlayerInventorySlot(4, OFFSET));
    }
}
