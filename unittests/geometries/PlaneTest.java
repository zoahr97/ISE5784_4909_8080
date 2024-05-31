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
    void testFindIntersections(){
        Plane p =new Plane(new Point(1, 1, 1),new Vector(1,1,1));
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============

        //TC01 start not in the plane and Intsersect the plane
        result = p.findIntersections(new Ray(new Point(0, 1, 1), new Vector(1, 0, 1)));
        assertEquals(result, List.of(new Point(0.5, 1, 1.5)), "Error start not in the plane and Intsersect the plane");

        //TC02 start not in the plane and not Intsersect the plane
        result = p.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "start not in the plane and not Intsersect the plane");

        // =============== Boundary Values Tests ==================

        //TC03 on the plane and not Intsersect the plane (paralel)
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(0, -1, 1)));
        assertNull(result, "on the plane and not Intsersect the plane (paralel)");

        //TC04 not on the plane and not Intsersect the plane (paralel(up))
        result = p.findIntersections(new Ray(new Point(5, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "not on the plane and not Intsersect the plane (paralel(up))");

        //TC05 not on the plane and not Intsersect the plane (paralel(down))
        result = p.findIntersections(new Ray(new Point(-1, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "not on the plane and not Intsersect the plane (paralel(down))");

        //TC06 perpendicular to the plane (before)
        result = p.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1)));
        assertEquals(result, List.of(new Point(1, 1, 1)), "perpendicular to the plane (before)");

        //TC07 perpendicular to the plane (on)
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 1, 1)));
        assertNull(result, "perpendicular to the plane (on)");

        //TC08 perpendicular to the plane (after)
        result = p.findIntersections(new Ray(new Point(2, 2, 2), new Vector(1, 1, 1)));
        assertNull(result, "perpendicular to the plane (after)");

        //TC09 starts on the normal but the ray not on the plane
        result = p.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 0, 0)));
        assertNull(result, "starts on the normal but the ray not on the plane");

        //TC10 starts on the normal and the ray on the plane
        result = p.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, -1, 1)));
        assertNull(result, "starts on the normal and the ray on the plane");

        //TC11 starts on the normal and the ray not on the plane
        result = p.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0)));
        assertNull(result, "starts on the normal and the ray not on the plane");
    }
}