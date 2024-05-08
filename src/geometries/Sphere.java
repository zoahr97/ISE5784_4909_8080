package geometries;

import primitives.Point;
import primitives.Vector;
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
     * @param p The point on the surface of the sphere at which to calculate the normal vector.
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal(Point p){
        return null;
    }

}
