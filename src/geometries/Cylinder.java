package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/** Cylinder class which represents the location of a cylinder in space
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Cylinder extends Tube
{
    /** the height of the Cylinder*/
    private final double height;


    public Cylinder(double radius,Ray axisRay, double height) {
        super(radius,axisRay);
        this.height = height;
    }
    /*
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }

/**
    public Vector getNormal(Point p)
    {
        return null;
    }
 */

    @Override
    public String toString() {
       return "Cylinder{" +
            "height=" + height +
            "axisRay=" + axis +
            ", radius=" + radius +
            '}';
    }

    /**
     * Calculates and returns the normal vector of the object at the given point.
     * The normal vector is calculated based on the axis of the object and the given point.
     *
     * @param point A {@link Point} object representing the point at which the normal vector is calculated.
     * @return The normal vector as a {@link Vector} object.
     * @throws UnsupportedOperationException if the normal vector cannot be calculated for the object.
     */


    @Override
    public Vector getNormal(Point point) throws UnsupportedOperationException {
        Point head = axis.getHead();
        Vector dir = axis.getDirection();

        // Check if the given point is the same as the base point of the axis
        if (point.equals(head))
            return dir;

        // Calculate the projection of (point - p0) onto the axis ray
        Vector u = point.subtract(head);

        // Calculate the distance from p0 to the object in front of the given point
        double t = alignZero(u.dotProduct(dir));

        // If the given point is at the base of the object or at the top of the object
        if (t == 0 || isZero(height - t))
            return dir;

        // Calculate the other point on the axis facing the given point
        Point o = head.add(dir.scale(t));

        // Calculate the normalized vector from the given point to the other point on the axis
        return point.subtract(o).normalize();
    }

}

