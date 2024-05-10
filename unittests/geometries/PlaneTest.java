package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void testPlane() {
        // ====Boundary Values Tests ====
        // TC10: two points are the same
        Point p1 = new Point( 1,  2, 3);
        Point p2 = new Point( 1,  2,  3);
        Point p3= new Point( 2,  3,  4);
        assertThrows (IllegalArgumentException.class, ()->new Plane (p1, p2, p3), "ERROR: two points are the same");
        //TC11: three points are on the same line
        Point p4 = new Point(1,  1,  1);
        Point p5 = new Point( 1,  1,  2);
        Point p6 = new Point( 1,  1,  3);
        assertThrows (IllegalArgumentException.class, () -> new Plane (p4, p5, p6), "ERROR: three points are on the same line");
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
        Point p3= new Point( 3,  4, 6);
        Plane plane = new Plane (p1, p2, p3);
        Vector vec = (new Vector ( 1, -1, 0)).normalize();
        assertTrue((plane.getNormal().equals (vec) || plane.getNormal().equals (vec.scale(  -1.0))),  "ERROR: getNormal() wrong value");
        //TC02 if the vector is normal
        assertEquals( 1, plane.getNormal().length(),  0.000001,  "ERROR: the vector was not normal");
    }
}