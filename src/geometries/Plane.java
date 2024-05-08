package geometries;
import primitives.Point;
import primitives.Vector;
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
     *
     * @param vertex the first point used to define the plane
     * @param vertex1 the second point used to define the plane
     * @param vertex2 the third point used to define the plane
     */
    public Plane(Point vertex, Point vertex1, Point vertex2) {/*ToDo implement const*/
        this.q = vertex;
        this.normal = null;
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
     * getter
     *
     * Returns the normal vector of the object.
     *
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal() {
        return normal;/*ToDo calculate normal*/

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
}
