package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Plane class which represents the location of a plane in space
 *  @author Dvora Enav and Zohar Tamsut
 */

public class Plane implements Geometry{

    /**
     *  point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane, normalized to a unit vector.
     */
    private final Vector normal;
    /**
     * Constructor to initialize a Plane object with three points.
     *the normal is calculating by the formula from lesson:cross product between 2 vectors
     * @param p1 the first point used to define the plane
     * @param p2 the second point used to define the plane
     * @param p3 the third point used to define the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.normal= p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
        this.q= p1;
    }
    /**
     * Constructor to initialize Plane object with a point and a normal vector.
     *
     * @param q    the point value
     * @param normal the normal vector value
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * Returns the point on the plane.
     *
     * @return The point on the plane as a {@link Point} object.
     */

    public Point getQ() {
        return q;
    }

    /**
     * getter
     *
     * Returns the normal vector of the object.
     *
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal() {
        return normal;
    }


    /**
     * implementation of {@link geometries.Geometry#getNormal(Point)}
     *
     * Returns the normal vector of the object.
     * @param vertex A {@link Point} object representing a point on the object. This parameter may be unused
     *              in the implementation of this method.
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal(Point vertex) {
        return normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
