package renderer;

import geometries.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import static org.junit.jupiter.api.Assertions.assertEquals;


@Nested
class IntegretionTest {
    int sum = 0;

    private int helper(Camera camera, Intersectable shape, int sum) {
        Geometries geo = new Geometries();
        geo.add(shape);
        sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRay(3, 3, j, i);
                var intersections = geo.findIntersections(ray);
                sum += (intersections == null) ? 0 : intersections.size();
            }
        }
        return sum;
    }

    private final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(1);
    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setLocation(new Point(0, 0, 0.5))
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(1);
    private final Camera.Builder cameraBuilder3 = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(1);

    IntegretionTest() throws CloneNotSupportedException {
    }

    @Test
    void testSphere() throws CloneNotSupportedException {

        // tc0: Intersection test with a sphere of radius 1 at (0, 0, -3)
        sum = helper(cameraBuilder1.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -3), 1),
                2);
        assertEquals(2, sum, "Number of intersections is incorrect");

        // tc01: Intersection test with a sphere of radius 2.5 at (0, 0, -2.5)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -2.5), 2.5), 18);
        assertEquals(18, sum, "Number of intersections is incorrect");

        // tc02: Intersection test with a sphere of radius 2 at (0, 0, -2)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -2), 2), 10);
        assertEquals(10, sum, "Number of intersections is incorrect");

        // tc03: Intersection test with a sphere of radius 4 at (0, 0, -3.5)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -3.5), 4), 9);
        assertEquals(9, sum, "Number of intersections is incorrect");

        // tc04: Intersection test with a sphere of radius 0.5 at (0, 0, 1)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, 1), 0.5), 0);
        assertEquals(0, sum, "Number of intersections is incorrect");
    }


    @Test
    void testTrinigle() throws CloneNotSupportedException {
// tc0: Intersection test with a sphere of radius 1 at (0, 0, -3)
        //sum = helper(cameraBuilder1.setVpSize(3, 3).build(), new Trinigle(new Point(0, 0, -3), 1),2);
//assertEquals(2, sum, "Number of intersections is incorrect");

// tc01: Intersection test with a sphere of radius 2.5 at (0, 0, -2.5)
        sum = helper(cameraBuilder3.setVpSize(3, 3).build(), new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)),1);
        assertEquals(1, sum, "Number of intersections is incorrect");

        sum = helper(cameraBuilder3.setVpSize(3, 3).build(), new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)),2);
        assertEquals(2, sum, "Number of intersections is incorrect");
    }

    public void main() {
    }

       @Test
       void testPlane() throws CloneNotSupportedException {
// Plane 1: 9 Intersection Points
           Camera camera = cameraBuilder3.setVpSize(3, 3).build();
           Plane plane1 = new Plane(new Point(0, 0, -2), new Vector(0, 0, 1));
           int sum = helper(camera, plane1,9);
           assertEquals(9, sum, "Number of intersections for Plane 1 is incorrect");

           // Plane 2: 9 Intersection Points
           Plane plane2 = new Plane(new Point(0, 0, -1), new Vector(0, 0, 1));
           sum = helper(camera, plane2,9);
           assertEquals(9, sum, "Number of intersections for Plane 2 is incorrect");

           // Plane 3: 6 Intersection Points
           Plane plane3 = new Plane(new Point(0, 0, -3), new Vector(0, 1, 1).normalize());
           sum = helper(camera, plane3,6);
           assertEquals(6, sum, "Number of intersections for Plane 3 is incorrect");
       }




}
