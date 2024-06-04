package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Geometries class.
 * Tests the findIntersections method to ensure it correctly finds intersection points
 * between a given ray and various geometric objects (Sphere, Plane, Triangle).
 */

class GeometriesTest {


    @Test
    void testFindIntersections(){
        Geometries geometry = new Geometries();

        geometry.add(new Plane(new Point(0,0,1),new Point(0,1,0),new Point(1,0,0)));
        geometry.add(new Cylinder(1,new Ray(new Point(1,1,1),new Vector(1,1,1)),2));
        geometry.add(new Sphere(new Point(1,0,0),1));
        geometry.add(new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1)));
        geometry.add(new Tube(1,new Ray(new Point(0,0,1),new Vector(0,0,1))));
        // ============ Equivalence Partitions Tests ==============
        // TC01: some of the geometries intersect with the ray
        assertEquals(3,geometry.findIntersections(new Ray(new Point(0,0,0),new Vector(1,1,1))).size(),
                "more then one intsersections");

        // =============== Boundary Values Tests ==================
        // TC02: no geometry intersect with the ray
        assertNull(geometry.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))),
                "Ray does not intersect with any geometry");

        // TC03: only one geometry intersect with the ray
        assertEquals(1,geometry.findIntersections(new Ray(new Point(0,0,0),new Vector(0,0,1))).size(),
                "one intsersections");

        // TC04: all geometries intersect with the ray
        assertEquals(4,geometry.findIntersections(new Ray(new Point(-2,-2,-2),new Vector(1,1,1))).size()
                ,"all intsersections");

        // TC05: empty list
        assertNull(geometry.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))),
                "empty list");
    }

}






