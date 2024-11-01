package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Tube class represents a tube object in a 3D space.
 * It is defined by a radius and an axis ray.
 *
 * @author Dvora Enav and Zohar Tamsut
 */

public class Tube extends RadialGeometry {

    /**
     * The axis ray of the tube.
     */
    protected Ray axis;

    /**
     * Constructs a new Tube object with the specified radius and axis ray.
     *
     * @param radius the radius of the tube.
     */
    public Tube(double radius) {
        super(radius);
    }

    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axis = axisRay;
    }

    /**
     * @return the axis of the ray
     */
    public Ray getAxisRay() {
        return axis;
    }

    @Override
    public Vector getNormal(Point point) {

        Point head = axis.getHead();
        Vector dir = axis.getDirection();

        // Check if the given point is equal to the starting point of the axis ray
        if (point.equals(head)) {
            throw new IllegalArgumentException("point cannot be equal to head");
        }


        // Calculate the parameter t by taking the dot product of the vector from the
        // starting point of the axis ray to the given point with the direction vector of the axis ray
        double t = alignZero(point.subtract(head).dotProduct(dir));


        // If t is 0, return the normalized vector from the given point to the starting point of the axis ray
        if (t == 0) {
            // The point is against the axis start point
            // Return the vector from the given point to the start of the ray, normalized
            return point.subtract(head).normalize();
        }
        // direction vector of the axis ray to the starting point of the axis ray and Return the normalized vector from the given point to the calculated projection as the normal vector
        return point.subtract(axis.getPoint(t)).normalize();
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
