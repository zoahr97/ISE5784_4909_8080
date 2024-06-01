package primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTests {

    @Test
    void testGetPoint() {
        /**
         * Tests the getPoint method of the Ray class.
         * The method should return the correct point at the specified distance
         * from the ray's origin along its direction.
         */
        // Arrange
        Point p0 = new Point(1, 2, 3);
        Vector v = new Vector(1, 0, 0);
        Ray ray = new Ray(p0, v);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Positive distance
        assertEquals(new Point(4, 2, 3),
                ray.getPoint(3),
                "Bad point for positive distance");

        // TC02: Negative distance
        assertEquals(new Point(-2, 2, 3),
                ray.getPoint(-3),
                "Bad point for negative distance");

        // =============== Boundary Values Tests ==================
        // TC11: Zero distance
        assertEquals(p0,
                ray.getPoint(0),
                "Bad point for zero distance");
    }
}
