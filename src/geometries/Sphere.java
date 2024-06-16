package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class which represents the location of a Sphere in space
 *
 * @author Dvora Enav and Zohar Tamsut
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
        Vector toCenter = center.subtract(ray.getHead());

        // Compute the projection of toCenter onto the ray's direction
        double projectionLength = alignZero(ray.getDirection().dotProduct(toCenter));

        // Compute the distance from the ray to the center of the sphere
        double perpendicularDistance = alignZero(Math.sqrt(toCenter.lengthSquared() - projectionLength * projectionLength));
        if (perpendicularDistance >= radius) return null; // No intersection, the ray is outside the sphere

        // Compute the half-chord length
        double halfChordLength = alignZero(Math.sqrt(radius * radius - perpendicularDistance * perpendicularDistance));

        // Compute the distances along the ray to the intersection points
        double intersection1 = alignZero(projectionLength - halfChordLength);
        double intersection2 = alignZero(projectionLength + halfChordLength);

        // Create and return the list of intersection points
        if (intersection1 > 0 && intersection2 > 0) {
            return List.of(ray.getPoint(intersection1), ray.getPoint(intersection2)); // Two intersection points
        } else if (intersection1 > 0) {
            return List.of(ray.getPoint(intersection1)); // One intersection point (intersection1 > 0)
        } else if (intersection2 > 0) {
            return List.of(ray.getPoint(intersection2)); // One intersection point (intersection2 > 0)
        } else {
            return null; // No intersection, both intersection1 and intersection2 are non-positive
        }
    }
}