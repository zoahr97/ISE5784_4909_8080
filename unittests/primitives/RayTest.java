package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        // Equivalence Partitions Test: Point in the middle is the closest
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(0.5, 0, 0); // Closest point
        Point p3 = new Point(2, 2, 2);
        List<Point> pointList=new LinkedList<Point>();
        pointList.add(p1);
        pointList.add(p2);
        pointList.add(p3);
        assertEquals(p2, ray.findClosestPoint(pointList), "Closest point is in the middle of the list");
// Boundary Test: Empty list
        List<Point> pointList2=new LinkedList<Point>();
        assertNull(ray.findClosestPoint(pointList2),"empty list needs to return null");
// Boundary Test: First point is the closest
        Point p4 = new Point(0.1, 0, 0); // Closest point
        pointList2.add(p4);
        pointList2.add(p1);
        pointList2.add(p3);
        assertEquals(p4,ray.findClosestPoint(pointList2),"First point is the closest");
        // Boundary Test: Last point is the closest
        pointList2.clear();
        pointList2.add(p3);
        pointList2.add(p1);
        pointList2.add(p4);
        assertEquals(p4,ray.findClosestPoint(pointList2),"Last point is the closest");
    }
}