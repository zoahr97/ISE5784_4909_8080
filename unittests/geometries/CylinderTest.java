package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Cylinder class.
 * Tests the getNormal method to ensure it correctly computes the normal vector
 * at various points on the cylinder's surface, including its bases and round surface.
 */

class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Cylinder c = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test normal to a point on the round surface
        Vector normalRoundSurface = c.getNormal(new Point(1, 0, 0));
        assertEquals(new Vector(0, 0, -1), normalRoundSurface,
                "TC01: Wrong normal to a point on the round surface");

        // TC02: Test normal to a point on the bottom base
        Vector normalBottomBase = c.getNormal(new Point(0, 0, -1));
        assertEquals(new Vector(0, 0, -1), normalBottomBase,
                "TC02: Wrong normal to a point on the bottom base");

        // TC03: Test normal to a point on the top base
        Vector normalTopBase = c.getNormal(new Point(0, 0, 1));
        assertEquals(new Vector(0, 0, 1), normalTopBase,
                "TC03: Wrong normal to a point on the top base");

        // =============== Boundary Values Tests ==================


        // TC04: Test normal to the center of the bottom base
        Vector normalCenterBottomBase = c.getNormal(new Point(0, 0, 0));
        assertEquals(new Vector(0, 0, -1), normalCenterBottomBase,
                "TC06: Wrong normal to the center of the bottom base");

        // TC05: Test normal to the center of the top base
        Vector normalCenterTopBase = c.getNormal(new Point(0, 0, 2));
        assertEquals(new Vector(0, 0, 1), normalCenterTopBase,
                "TC07: Wrong normal to the center of the top base");
    }


}