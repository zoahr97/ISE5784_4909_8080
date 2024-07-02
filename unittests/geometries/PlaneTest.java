package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Plane class.
 * Tests the findIntersections method to ensure it correctly finds intersection points
 * between a given ray and a plane.
 */
class PlaneTest {

    @Test
    void testPlane() {
        // ====Boundary Values Tests ====
        // TC10: two points are the same
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 2, 3);
        Point p3 = new Point(2, 3, 4);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p3),
                "ERROR: two points are the same");
        //TC11: three points are on the same line
        Point p4 = new Point(1, 1, 1);
        Point p5 = new Point(1, 1, 2);
        Point p6 = new Point(1, 1, 3);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p4, p5, p6),
                "ERROR: three points are on the same line");
    }


    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Three non-collinear points
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 3, 4);
        Point p3 = new Point(3, 4, 6);
        Plane plane = new Plane(p1, p2, p3);
        Vector vec = (new Vector(1, -1, 0)).normalize();
        assertTrue((plane.getNormal().equals(vec) || plane.getNormal().equals(vec.scale(-1.0))),
                "ERROR: getNormal() wrong value");
        //TC02 if the vector is normal
        assertEquals(1, plane.getNormal().length(), 0.000001,
                "ERROR: the vector was not normal");
    }

    @Test
    void testFindIntersections() {
        Plane p1 = new Plane(new Point(-1, 0, 0), new Point(0, -1, 0), new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Ray r1 = new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1));
        assertEquals(List.of(new Point(0, 0, 1)), p1.findIntersections(r1),
                "findIntersections() wrong result");

        //TC02: Ray does not intersect the plane
        Ray r2 = new Ray(new Point(0, -2, 0), new Vector(0, 2, 3));
        assertNull(p1.findIntersections(r2),
                "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray is parallel to the plane and included in the plane
        Ray r3 = new Ray(new Point(0, -1, 0), new Vector(0, 1, 1));
        assertNull(p1.findIntersections(r3),
                "findIntersections() for parallel and included Ray is wrong");

        //TC12: Ray is parallel to the plane and not included in the plane
        Ray r4 = new Ray(new Point(0, -2, 0), new Vector(0, 2, 2));
        assertNull(p1.findIntersections(r4),
                "findIntersections() for parallel and not included Ray is wrong");

        //TC13: Ray is orthogonal to the plane and p0 before the plane
        Ray r5 = new Ray(new Point(0, 3, 0), new Vector(-1, -1, 1));
        assertEquals(List.of(new Point(-1.3333333333333335, 1.6666666666666665, 1.3333333333333335)),
                p1.findIntersections(r5),
                "findIntersections() for orthogonal Ray before the plane is wrong");

        //TC14: Ray is orthogonal to the plane and p0 in the plane
        Ray r6 = new Ray(new Point(0, 0, 1), new Vector(1, 1, -1));
        assertNull(p1.findIntersections(r6),
                "findIntersections() for orthogonal Ray in the plane is wrong");

        //TC15: Ray is orthogonal to the plane and p0 after the plane
        Ray r7 = new Ray(new Point(0, 3, 0), new Vector(1, 1, -1));
        assertNull(p1.findIntersections(r7),
                "findIntersections() for orthogonal Ray after the plane is wrong");

        //TC16: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        Ray r8 = new Ray(new Point(1.45, -1.86, 0.6), new Vector(-1.45, -0.14, -0.6));
        assertNull(p1.findIntersections(r8),
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");

        //TC17: Ray is neither orthogonal nor parallel to the plane and begins in the same point (p0 equals q)
        Ray r9 = new Ray(new Point(-1, 0, 0), new Vector(1, 0, 2));
        assertNull(p1.findIntersections(r9),
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");
    }
}