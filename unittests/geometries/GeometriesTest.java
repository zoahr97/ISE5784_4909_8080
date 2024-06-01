package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class GeometriesTest {


    @Test

    void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(0, 0, 5), 3); // ספירה שנמצאת מרכזית במרחק 5 מהמקור
        Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)); // מישור הנמצא בגובה 0 מהמקור, מקביל לציר ה־Z
        Triangle triangle = new Triangle(new Point(-2, -2, 0), new Point(2, -2, 0), new Point(0, 2, 0)); // משולש במישור ה־XY
        Geometries geometries = new Geometries();
        geometries.add(sphere, plane, triangle);

        // =============== Boundary Values Tests ==================
        //TC01 empty collection
        assertNull(geometries.findIntersections(new Ray(new Point(10, 10, 10), new Vector(0, 0, 1))),
                "Expected no intersections when the geometry list is empty");

        // TC02: some of the geometries intersect with the ray
        assertEquals(2,geometries.findIntersections(new Ray(new Point(5,0,0), new Vector(0, 0, -1))),
                "Expected two intersection");

//
//        // TC03: only one geometry intersect with the ray
        assertEquals(1,geometries.findIntersections(new Ray(new Point(2,0,0), new Vector(1, 0, 0))),
                "Expected one intersection");

        // TC04: no geometry intersect with the ray
        assertNull(geometries.findIntersections(new Ray(new Point(0,0,-10), new Vector(0, 0, 1))),
                "Ray does not intersect with any geometry");

//
//        // ============ Equivalence Partitions Tests ==============
//        // TC05: all geometries intersect with the ray
assertEquals(3,geometries.findIntersections(new Ray(new Point(-3,0,0), new Vector(0, 0, -1))),
               "Expected three intersection");


    }
}