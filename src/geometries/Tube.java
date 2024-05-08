package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
/**
 * The Tube class represents a tube object in a 3D space.
 * It is defined by a radius and an axis ray.
 *  @author Dvora Enav and Zohar Tamsut
 */

public class Tube extends RadialGeometry {

    /** The axis ray of the tube. */
    protected Ray axis;

    /**
     * Constructs a new Tube object with the specified radius and axis ray.
     *
     * @param radius the radius of the tube.
     */
    public Tube(double radius) {
        super(radius);
    }

    /**
     * Calculates the normal vector of a surface at a given point, based on an axis ray.
     *
     * @param p The point on the surface for which the normal vector needs to be calculated.
     * @return The normalized normal vector at the given point.
     */
    public Vector getNormal(Point p){
        return null;
    }
}
