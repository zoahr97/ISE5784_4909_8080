package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Sphere class which represents the location of a Sphere in space
 *  @author Dvora Enav and Zohar Tamsut
 */

public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private Point center;

    /**
     * Constructor for a sphere object.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates and returns the normal vector to the surface of the sphere at a given point.
     *
     * @param point The point on the surface of the sphere at which to calculate the normal vector.
     * @return The normal vector as a {@link Vector} object.
     */

    public Vector getNormal(Point point) {
        // Calculate the vector from the center of the sphere to the given point
        //  and Normalize the vector to obtain the normal vector
        return point.subtract(center).normalize();
    }


    /**
     * Finds intersection points between the sphere and a given ray.
     *
     * @param ray The ray to check for intersections with the sphere.
     * @return A list of intersection points, or null if no intersections exist.
     */
    public List<Point> findIntersections(Ray ray) {
        // Check if the ray starts at the center of the sphere
        if (ray.getHead().equals(center)) {
            return List.of(ray.getPoint(radius)); // Return a single intersection point
        }

        // Compute the vector from the ray's starting point to the center of the sphere
        Vector v = center.subtract(ray.getHead());

        // Compute tm, the projection of v onto the ray's direction
        double tm = alignZero(ray.getDirection().dotProduct(v));

        // Check if the ray is tangent to the sphere
        double d = alignZero(Math.sqrt(v.lengthSquared() - tm * tm));
        if (d >= radius) return null; // No intersection, the ray is outside the sphere

        // Compute th, the distance from the point of intersection to the sphere's surface
        double th = alignZero(Math.sqrt(radius * radius - d * d));

        // Compute t1 and t2, the distances along the ray to the intersection points
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // Create and return the list of intersection points
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2)); // Two intersection points
        } else if (t1 > 0) {
            return List.of(ray.getPoint(t1)); // One intersection point (t1 > 0)
        } else if (t2 > 0) {
            return List.of(ray.getPoint(t2)); // One intersection point (t2 > 0)
        } else {
            return null; // No intersection, both t1 and t2 are non-positive
        }
    }
}