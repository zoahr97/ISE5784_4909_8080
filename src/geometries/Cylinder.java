package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cylinder class which represents the location of a cylinder in space
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Cylinder extends Tube {
    /**
     * the height of the Cylinder
     */
    private final double height;

    /**
     * cylinder constructor gets radius,ray and height
     *
     * @param radius  is the radius of the cylinder
     * @param axisRay is the axis ray of the cylinder
     * @param height  is the height of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
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
     * @param p A {@link Point} object representing the point at which the normal vector is calculated.
     * @return The normal vector as a {@link Vector} object.
     * @throws UnsupportedOperationException if the normal vector cannot be calculated for the object.
     */


    @Override
    public Vector getNormal(Point p) {
        // the formula to calculate the normal of a point on the cylinder is:
        // if the point is on the bottom or top of the cylinder,
        // the normal is the vector from the point to the center of the cylinder
        // if the point is on the side of the cylinder,
        // the normal is the vector from the point to the axis
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();
        // if the point is the head of the axis
        if (p.equals(p0)) {
            return v.scale(-1);
        }
        Vector delta = p.subtract(p0);
        double t = v.dotProduct(delta);
        Point o;

        // if t=0, the point is on the axis
        if (t <= 0) {
            return v.scale(-1);
        } else {
            o = p0.add(v.scale(t));
        }

        if (p.equals(o)) {
            return p.subtract(p0).normalize();
        }
        // if the point is on the bottom or top of the cylinder
        if ((t - height) == 0) {
            return v;
        }
        // if the point is on the side of the cylinder
        return super.getNormal(p);

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return super.findGeoIntersectionsHelper(ray);
    }
}

