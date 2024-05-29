package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Arrays;
import java.util.List;

/**
 * Sphere class which represents the location of a Sphere in space
 *  @author Dvora Enav and Zohar Tamsut
 */

public class Sphere extends RadialGeometry{
    /**
     * The center point of the sphere.
     */
    private Point center;
    /**
     * Constructor for a sphere object.
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center,double radius) {
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
    public List<Point> findIntsersections(Ray ray) {
        return List.of();
    }
}
