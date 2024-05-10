package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PointTest {

    Point p = new Point(1,2,3);
    @Test
    void testAdd()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Point(2, 3, 4), new Point(1, 1, 1).add(new Vector(1, 2, 3)), //
                "Wrong point add");

        // =============== Boundary Values Tests ==================
        // there are no boundary tests
    }

    @Test
    void testDistanceSquared()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d, new Point(1, 1, 1).distanceSquared(new Point(2, 3, 4)), 0.0001, //
                "Wrong squared distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, new Point(1, 2, 3).distanceSquared(p), 0.0001, //
                "Wrong squared distance between the point and itself");
    }

    @Test
    void testDistance()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(Math.sqrt(14), new Point(1, 1, 1).distance(new Point(2, 3, 4)), 0.0001, //
                "Wrong distance between the point and itself");

        // =============== Boundary Values Tests ==================
        // TC11: test distance with the same point
        assertEquals(0d, p.distance(p), 0.0001, //
                "Wrong distance between the point and itself");
    }

    @Test
    void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p), //
                "Wrong point subtract");

        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same point
        assertThrows(IllegalArgumentException.class, () -> new Point(1, 2, 3).subtract(p), //
                "Subtract P from P must throw exception");
    }
}