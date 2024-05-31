package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(
                IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1),
                        new Point(0, 1, 0),
                        new Point(1, 0, 0),
                        new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(
                IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class,
                () -> new Polygon(
                        new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)),
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(
                IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(
                IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(
                IllegalArgumentException.class,
                () -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))), "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */

    @Test
    void testFindIntersections() {
        Polygon p = new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection in the polygon
        Ray r1 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        List<Point> points = p.findIntersections(r1);
        assertEquals(1, points.size(), "Wrong number of points");
        assertEquals(new Point(0.5, 0.5, 0), points.get(0), "Ray intersection isn't working on polygon");

        //TC02: Ray intersect outside Triangle against edge
        Ray r2 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 0));
        assertNull(p.findIntersections(r2),
                "findIntersections() wrong result");

        //TC03: Ray intersect outside Triangle against vertex
        Ray r3 = new Ray(new Point(-1, -1, 1), new Vector(2, 3, -1));
        assertNull(p.findIntersections(r3),
                "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray intersect on edge
        Ray r4 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, -1, 1));
        assertNull(p.findIntersections(r4),
                "findIntersections() Ray intersect on edge wrong result");

        //TC12: Ray intersect in vertex
        Ray r5 = new Ray(new Point(0, 0, 1), new Vector(1, 1, -1));
        assertNull(p.findIntersections(r5),
                "findIntersections() Ray intersect in vertex wrong result");

        //TC13: Ray intersect on edge's continuation
        Ray r6 = new Ray(new Point(-1, -1, 1), new Vector(-1, -1, -1));
        assertNull(p.findIntersections(r6),
                "findIntersections() Ray intersect on edge's continuation wrong result");

    }
}
