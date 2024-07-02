package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RayTests {

    @Test
    void testGetPoint() {
        /**
         * Tests the getPoint method of the Ray class.
         * The method should return the correct point at the specified distance
         * from the ray's origin along its direction.
         */

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

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        Point a = new Point(1, 1, 0);
        Point b = new Point(2, 2, 0);
        Point c = new Point(3, 3, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01 the closest point is in the middle of the list
        List<Point> points = new LinkedList<>();
        points.add(b);
        points.add(a);
        points.add(c);
        assertEquals(a, ray.findClosestPoint(points),
                "testFindClosestPoint: the closest point is in the middle of the list, expected " + a);

        // =============== Boundary Values Tests ==================

        // TC11 list of points is empty
        assertNull(ray.findClosestPoint(new LinkedList<>()),
                "testFindClosestPoint: list of points is empty, expected null");

        // TC02 the closest point is the first point in the list
        points = new LinkedList<>();
        points.add(a);
        points.add(b);
        points.add(c);
        assertEquals(a, ray.findClosestPoint(points),
                "testFindClosestPoint: the closest point is the first point in the list, expected " + a);

        // TC03 the closest point is at the end of the list
        points = new LinkedList<>();
        points.add(b);
        points.add(c);
        points.add(a);
        assertEquals(a, ray.findClosestPoint(points),
                "testFindClosestPoint: the closest point is at the end of the list, expected " + a);

    }
}
