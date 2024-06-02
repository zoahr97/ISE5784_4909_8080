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

/*
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
    */
    @Test
    void testFindIntersections(){
        Geometries Geomet=new Geometries();

        Geomet.add(new Plane(new Point(0,0,1),new Point(0,1,0),new Point(1,0,0)));
        Geomet.add(new Cylinder(1,new Ray(new Point(1,1,1),new Vector(1,1,1)),2));
        Geomet.add(new Sphere(new Point(1,0,0),1));
        Geomet.add(new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1)));
        Geomet.add(new Tube(1,new Ray(new Point(0,0,1),new Vector(0,0,1))));
        // ============ Equivalence Partitions Tests ==============
        // TC01: some of the geometries intersect with the ray
        assertEquals(3,Geomet.findIntersections(new Ray(new Point(0,0,0),new Vector(1,1,1))).size(),"more then one intsersections");

        // =============== Boundary Values Tests ==================
        // TC02: no geometry intersect with the ray
        assertNull(Geomet.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "Ray does not intersect with any geometry");

        // TC03: only one geometry intersect with the ray
        assertEquals(1,Geomet.findIntersections(new Ray(new Point(0,0,0),new Vector(0,0,1))).size(),"one intsersections");

        // TC04: all geometries intersect with the ray
        assertEquals(4,Geomet.findIntersections(new Ray(new Point(-2,-2,-2),new Vector(1,1,1))).size(),"all intsersections");

        // TC05: empty list
        assertNull(Geomet.findIntersections(new Ray(new Point(10,10,10),new Vector(0,0,1))), "empty list");
    }

}






