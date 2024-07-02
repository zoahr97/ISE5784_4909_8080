package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the Sphere class.
 * Tests the getNormal method to ensure it correctly computes the normal vector
 * at various points on the sphere's surface.
 * Tests the findIntersections method to ensure it correctly identifies the intersection points
 * between a ray and the sphere.
 */

class SphereTest {
    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        Sphere sphere = new Sphere(new Point(0, 0, 0), 5);

        // ============ Equivalence Partitions Tests ==============//
        // TC01: tests for calculation of normal to the sphere//
        assertEquals(
                new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)),
                "ERROR: The calculation of normal to the Sphere is not calculated correctly");

        // TC02:  Check if The vector is normalized
        assertEquals(
                1, sphere.getNormal(new Point(1, 1, 0)).length(),
                0.000001,
                "Error the vector was not normalized");

    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */

    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), v110));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(new Point(1.4114378277661475, 0.9114378277661476, 0), result2.get(0),
                "Ray starts inside sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), v110)), "Ray starts after sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        final var result3 = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
        assertEquals(result3, List.of(new Point(1, 1, 0)), "Error TC11");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp2, v310)), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(p01, new Vector(1, 0, 0)));
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result4,
                "Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        final var result5 = sphere.findIntersections(new Ray(gp1, v110));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0.644948974278318, 0.9348469228349534, 0)), result5,
                "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 points)
        final var result6 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), v110));
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(new Point(1.4114378277661475, 0.9114378277661476, 0), result6.get(0),
                "Ray starts inside");

        // TC16: Ray starts at the center (1 points)
        final var result7 = sphere.findIntersections(new Ray(new Point(1, 0, 0), v110));
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(new Point(1.7071067811865475, 0.7071067811865475, 0.0), result7.get(0),
                "Ray starts at the center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp1, v310.scale(-1))),
                "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), v110)), "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, -1, 0))),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(gp1, new Vector(-1, 1, 0))),
                "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, -1, 0))),
                "Ray starts after the tangent point");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
        // center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 1, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }

}