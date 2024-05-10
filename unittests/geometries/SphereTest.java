package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest
{
    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        Sphere sphere = new Sphere(new Point(0, 0, 0),5);

        // ============ Equivalence Partitions Tests ==============//
        // TC01: tests for calculation of normal to the sphere//
        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)),
                "ERROR: The calculation of normal to the Sphere is not calculated correctly");

        // TC02: if The vector is normal
        assertEquals(1,sphere.getNormal(new Point(1,1,0)).length(),0.000001,
                "Error the vector was not normal");

    }

}