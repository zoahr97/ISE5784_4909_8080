package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest
{
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        //=== Equivalence Partitions Tests ===
        //TC01: normal to triangle

        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 1);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector vec = new Vector(0.5, 0.5, 0.5).normalize();
        assertTrue(
                (triangle.getNormal(new Point(0.5, 0.5, 0.5)).equals(vec)) ,
                "ERROR: getNormal() wrong value");
        //TC02 if the vector is normalized
        assertEquals(
                1, triangle.getNormal(p1).length(),
                0.000001,
                "ERROR: the vector was not normalized");

    }


}