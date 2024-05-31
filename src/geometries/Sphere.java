package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Arrays;
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        // if the ray starts at the center of the sphere
        if (ray.getHead().equals(center)) {
            return List.of(ray.getPoint(radius));
        }
        //check if there is intsersection between them
        Vector v = center.subtract(ray.getHead());

        double tm = alignZero(ray.getDirection().dotProduct(v));

        //check if the ray is tangent to the sphere
        double d = alignZero(Math.sqrt(v.lengthSquared() - tm * tm));
        if (d >= radius) return null;
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        }
        if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        }
        return null;


    }


}
