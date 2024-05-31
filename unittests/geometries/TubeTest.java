package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(1,new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: if The calculation of normal is correctly

        assertEquals(new Vector(0, 0, 1),
                tube.getNormal(new Point(1, 0, 1)),
                "ERROR: The calculation of normal to the tube is not calculated correctly");

        // TC02: if The vector is normal
        assertEquals(1,tube.getNormal(new Point(2,1,0)).length() ,0.000001,"Error the vector was not normal");


        // =============== Boundary Values Tests ==================
        //Test when the point is orthogonal to the ray's head goes to the ZERO vector
        assertThrows(IllegalArgumentException.class, () -> {
                    tube.getNormal(new Point(0, 0, 0)); // use (0, 0, 0) instead of (0, 0, 1)
                },
                "ZERO vector is not allowed");
    }

    @Test
    void testFindIntersections() {
    }
}