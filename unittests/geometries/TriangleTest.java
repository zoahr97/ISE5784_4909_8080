package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Triangle class.
 * Tests the getNormal method to ensure it correctly computes the normal vector
 * and the findIntersections method to ensure it correctly identifies intersection points
 * with a given ray.
 */
class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        //=== Equivalence Partitions Tests ===
        //TC01: normal to triangle

        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 1);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector vec = new Vector(0.5, 0.5, 0.5).normalize();
        assertTrue(
                (triangle.getNormal(new Point(0.5, 0.5, 0.5)).equals(vec)),
                "ERROR: getNormal() wrong value");
        //TC02 if the vector is normalized
        assertEquals(
                1, triangle.getNormal(p1).length(),
                0.000001,
                "ERROR: the vector was not normalized");

    }

    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(3, 0, 0), new Point(0, 3, 0), new Point(0, 0, 4));

        // ============ Equivalence Partitions Tests ==============

        // TC01:   Ray intersect inside Triangle
        Point p = new Point(1.1195516811955168, 0.9464508094645081, 1.2453300124533);
        List<Point> result = triangle.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(0.12, 0.95, 1.25)));
        assertEquals(List.of(p), result, "wrong point values");

        // TC02: Ray intersect outside Triangle against edge
        assertNull(triangle.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1.93, -1.33, 1.86))),
                "Ray does not cross the triangle");

        //TC03: Ray intersect outside Triangle against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 2.39), new Vector(-0.17, -0.17, 2.07))),
                "Ray does not cross the triangle");

        // =============== Boundary Values Tests ==================
        //TC011: Ray intersect on edge
        assertNull(triangle.findIntersections(new Ray(new Point(0, -1, 0), new Vector(1.8, 1, 1.6))),
                "Ray does not cross the triangle");
        //TC06:  Ray intersect in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, -1, 0), new Vector(0, 1, 4))),
                "Ray does not cross the triangle");
        //TC07: Ray intersect on edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0.24, 0.01, 4.99))),
                "Ray does not cross the triangle");
    }

}