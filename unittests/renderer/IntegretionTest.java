package renderer;

import geometries.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IntegrationTest class is responsible for conducting integration tests
 * to validate the intersection calculations between rays and various geometric shapes.
 */
@Nested
class IntegrationTest {
    int sum = 0;

    /**
     * Helper method to calculate the sum of intersections between rays constructed from the camera
     * and the given geometric shape.
     *
     * @param camera the camera used to construct rays
     * @param shape the geometric shape to test for intersections
     * @param sum the initial sum of intersections
     * @return the total sum of intersections
     */
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

    String expection1 = "Number of intersections is incorrect";

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

    IntegrationTest() throws CloneNotSupportedException {
    }

    /**
     * Test method to validate intersection calculations with spheres.
     */
    @Test
    void testSphere() throws CloneNotSupportedException {
        // tc0: Intersection test with a sphere of radius 1 at (0, 0, -3)
        sum = helper(cameraBuilder1.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -3), 1), 2);
        assertEquals(2, sum, expection1);

        // tc01: Intersection test with a sphere of radius 2.5 at (0, 0, -2.5)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -2.5), 2.5), 18);
        assertEquals(18, sum, expection1);

        // tc02: Intersection test with a sphere of radius 2 at (0, 0, -2)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -2), 2), 10);
        assertEquals(10, sum, expection1);

        // tc03: Intersection test with a sphere of radius 4 at (0, 0, -3.5)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, -3.5), 4), 9);
        assertEquals(9, sum, expection1);

        // tc04: Intersection test with a sphere of radius 0.5 at (0, 0, 1)
        sum = helper(cameraBuilder2.setVpSize(3, 3).build(), new Sphere(new Point(0, 0, 1), 0.5), 0);
        assertEquals(0, sum, expection1);
    }

    /**
     * Test method to validate intersection calculations with triangles.
     */
    @Test
    void testTriangle() throws CloneNotSupportedException {
        // tc01: Intersection test with a triangle
        sum = helper(cameraBuilder3.setVpSize(3, 3).build(), new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 1);
        assertEquals(1, sum, expection1);

        sum = helper(cameraBuilder3.setVpSize(3, 3).build(), new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 2);
        assertEquals(2, sum, expection1);
    }

    public void main() {
    }

    /**
     * Test method to validate intersection calculations with planes.
     */
    @Test
    void testPlane() throws CloneNotSupportedException {
        // Plane 1: 9 Intersection Points
        Camera camera = cameraBuilder3.setVpSize(3, 3).build();
        Plane plane1 = new Plane(new Point(0, 0, -2), new Vector(0, 0, 1));
        int sum = helper(camera, plane1, 9);
        assertEquals(9, sum, expection1);

        // Plane 2: 9 Intersection Points
        Plane plane2 = new Plane(new Point(0, 0, -1), new Vector(0, 0, 1));
        sum = helper(camera, plane2, 9);
        assertEquals(9, sum, expection1);

        // Plane 3: 6 Intersection Points
        Plane plane3 = new Plane(new Point(0, 0, -3), new Vector(0, 1, 1).normalize());
        sum = helper(camera, plane3, 6);
        assertEquals(6, sum, expection1);
    }
}
