package net.minestom.server.coordinate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testPosCenter2D() {
        Pos pos = new Pos(1, 100, 10);
        assertNotNull(pos);

        Point centered = pos.center2D();
        assertInstanceOf(Pos.class, centered);

        assertNotNull(centered);
        assertNotEquals(pos, centered);
        assertEquals(1.5, centered.x());
        assertEquals(10.5, centered.z());
    }

    @Test
    void testVecCenter3D() {
        Point centered3D = Vec.ZERO.center3D();
        assertInstanceOf(Vec.class, centered3D);
        assertEquals(0.5, centered3D.x());
        assertEquals(0.5, centered3D.y());
        assertEquals(0.5, centered3D.z());
    }
}