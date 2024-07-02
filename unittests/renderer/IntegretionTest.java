package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IntegrationTest class is responsible for conducting integration tests
 * to validate the intersection calculations between rays and various geometric shapes.
 *
 * @author Dvora and Zohar
 */

class IntegrationTest {
    /**
     * Helper method to calculate the sum of intersections between rays constructed from the camera
     * and the given geometric shape.
     *
     * @param camera the camera used to construct rays
     * @param shape the geometric shape to test for intersections
     * @param sum the initial sum of intersections
     * @return the total sum of intersections
     */
    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder baseCameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(1).setImageWriter(new ImageWriter("base render test", 1000, 1000))
            .setLocation(new Point(1, 1, 1));
    int sum = 0;
    int nx = 3;
    int ny = 3;
    String expection1 = "Number of intersections is incorrect";

    IntegrationTest() throws CloneNotSupportedException {
    }


    /**
     * Counts the total number of intersections between rays constructed from the camera
     * and the given geometry within the view plane grid defined by nx and ny.
     *
     * @param camera    the Camera object used to construct rays
     * @param geometry  the Intersectable geometry to test for intersections with the rays
     * @return          the total number of intersection points found
     */
    private int countIntersections(Camera camera, Intersectable geometry) {
        int sum = 0;
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                Ray ray = camera.constructRay(nx, ny, j, i);
                var intersections = geometry.findIntersections(ray);
                sum += (intersections == null) ? 0 : intersections.size();
            }
        }
        return sum;
    }


    /**
     * Test method to validate intersection calculations with spheres.
     */
    @Test
    void testSphere() throws CloneNotSupportedException {
        // tc0: Intersection test with a sphere of radius 1 at (0, 0, -3)
        assertEquals(2,
                countIntersections(baseCameraBuilder.setLocation(Point.ZERO).setVpSize(3, 3).build(),
                        new Sphere(new Point(0, 0, -3), 1)),
                expection1);

        // tc01: Intersection test with a sphere of radius 2.5 at (0, 0, -2.5)
        assertEquals(18,
                countIntersections(baseCameraBuilder.setLocation(new Point(0, 0, 0.5)).setVpSize(3, 3).build(),
                        new Sphere(new Point(0, 0, -2.5), 2.5)),
                expection1);

        // tc02: Intersection test with a sphere of radius 2 at (0, 0, -2)
        assertEquals(10,
                countIntersections(baseCameraBuilder.setLocation(new Point(0, 0, 0.5)).setVpSize(3, 3).build(),
                        new Sphere(new Point(0, 0, -2), 2)),
                expection1);

        // tc03: Intersection test with a sphere of radius 4 at (0, 0, -3.5)
        assertEquals(9,
                countIntersections(baseCameraBuilder.setLocation(new Point(0, 0, 0.5)).setVpSize(3, 3).build(),
                        new Sphere(new Point(0, 0, -3.5), 4)),
                expection1);

        // tc04: Intersection test with a sphere of radius 0.5 at (0, 0, 1)
        assertEquals(0,
                countIntersections(baseCameraBuilder.setLocation(new Point(0, 0, 0.5)).setVpSize(3, 3).build(),
                        new Sphere(new Point(0, 0, 1), 0.5)),
                expection1);
    }

    /**
     * Test method to validate intersection calculations with planes.
     */
    @Test
    void testPlane() throws CloneNotSupportedException {
        // Base camera with initial settings
        Camera baseCamera = baseCameraBuilder.setVpSize(3, 3).build();

        // Plane 1: 9 Intersection Points
        Plane plane1 = new Plane(new Point(0, 0, -2), new Vector(0, 0, 1));
        assertEquals(9, countIntersections(baseCameraBuilder.setLocation(Point.ZERO).build(), plane1), expection1);

        // Plane 2: 9 Intersection Points
        Plane plane2 = new Plane(new Point(0, 0, -1), new Vector(0, 0, 1));
        assertEquals(9, countIntersections(baseCameraBuilder.setLocation(Point.ZERO).build(), plane2), expection1);

        // Plane 3: 6 Intersection Points
        Plane plane3 = new Plane(new Point(0, 0, -3), new Vector(0, 1, 1).normalize());
        assertEquals(6, countIntersections(baseCameraBuilder.setLocation(Point.ZERO).build(), plane3), expection1);
    }
    }