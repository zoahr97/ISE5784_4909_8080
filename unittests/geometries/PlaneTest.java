package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        //TC02  Check if the vector is normalized
        assertEquals(1, plane.getNormal().length(), 0.000001,
                "ERROR: the vector was not normalized");
    }


    @Test
    public void testFindIntersections() {
        Point p = new Point(1, 1, 1);//point on the plane
        Vector n = new Vector(0, -1, 1);//vector orthogonal to the point p
        Plane plane = new Plane(p, n);//create the plane with point and normal
                 // ============ Equivalence Partitions Tests ==============
                 //The ray is not parallel or orthogonal to the plane
                // TC01: Ray's line cuts the plane (1 point)
                assertEquals(plane.findIntersections(new Ray(new Point(1,1,3),
                new Vector(0,0,-1))),
                List.of(new Point(1,1,1)),
                "ERROR: findIntersections Ray's line is outside the Plane");
                // TC02: Ray's line is outside the Plane (0 points)
                assertNull(plane.findIntersections(new Ray(new Point(0,0,2),
                new Vector(1,1,0))) ,
                "ERROR: A ray is not perpendicular, not parallel and does not cut the plane");

              // =============== Boundary Values Tests ==================

                 // ** Group: The ray is parallel to the plane
                 // TC10: Ray's line on the plane (0 points)
                assertNull(plane.findIntersections(new Ray(new Point(1,1,1)
                ,new Vector(1,1,0))),
                "ERROR: Ray's line on of plane");
                 // TC11: Ray's line out of plane (0 points)
                 assertNull(plane.findIntersections(new Ray(new Point(0,0,2)
                ,new Vector(1,1,0))),
                "ERROR: Ray's line out of plane");

                 // ** Group: The ray is perpendicular to the plane
                 // TC12: Ray's line starts before the plane (1 point)
                 assertEquals(plane.findIntersections (new Ray(new Point(1, 1, 0),
                 new Vector(0, 0, 1))),List.of(new Point(1,1,1)),
                "ERROR: Ray's line starts before the plane");
                // TC13: Ray's line starts inside the plane (0 points)
                  assertNull(plane.findIntersections (new Ray(new Point(1, 1, 1)
                 ,new Vector(0, 0, 1))),
                 "ERROR: Ray's line starts inside the plane");
                // TC14: Ray's line starts after the plane (1 point)
                assertEquals(plane.findIntersections(new Ray(new Point(1, 1, 2),
                new Vector(0, 0, -1))),List.of(new Point(1,1,1)),
                "ERROR: Ray's line starts after the plane");

                 // ** Group: The ray is neither perpendicular nor parallel
                 // TC15: The head of the Ray's line starts exactly at the "reference point" (1 point)
                assertNull(plane.findIntersections (new Ray(new Point(1, 1, 1),
                new Vector(1, 1, 1))),
                "ERROR: The head of the Ray's line starts exactly at the reference point");
                 // TC16: Ray's line starts on the plane (1 point)
                 assertNull(plane.findIntersections (new Ray(new Point(2, 3, 1)
                ,new Vector(-1, -1, 1))),
                 "ERROR: Ray's line starts on the plane");

    }
}