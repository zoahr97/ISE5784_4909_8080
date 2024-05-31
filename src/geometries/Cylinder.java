package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/** Cylinder class which represents the location of a cylinder in space
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Cylinder extends Tube
{
    /** the height of the Cylinder*/
    private final double height;

    /**
     * cylinder constructor gets radius,ray and height
     * @param radius is the radius of the cylinder
     * @param axisRay is the axis ray of the cylinder
     * @param height is the height of the cylinder
     */
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

        // Calculate the projection of (point - head) onto the axis ray and Calculate the distance from head to the object in front of the given point
        double t = alignZero(point.subtract(head).dotProduct(dir));

        // Check if the given point is the same as the base point of the axis
        if (point.equals(head)||t == 0 || isZero(height - t))
            return dir;


        // Calculate the normalized vector from the given point to the other point on the axis
        return point.subtract(head.add(dir.scale(t))).normalize();
    }
    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }
}

