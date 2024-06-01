package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a flat, two-dimensional surface in three-dimensional space.
 * It is defined either by three points or by a point and a normal vector.
 * The normal vector indicates the orientation of the plane.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    private final Point q;

    /**
     * The normal vector to the plane, normalized to a unit vector.
     */
    private final Vector normal;

    /**
     * Constructs a Plane object from three points.
     * The normal vector is calculated using the cross product of two vectors formed by these points.
     *
     * @param p1 The first point used to define the plane.
     * @param p2 The second point used to define the plane.
     * @param p3 The third point used to define the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
        this.q = p1;
    }

    /**
     * Constructs a Plane object from a point and a normal vector.
     * The normal vector is automatically normalized.
     *
     * @param q      The point on the plane.
     * @param normal The normal vector to the plane.
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
     * Returns the normal vector of the plane.
     *
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Implementation of the {@link geometries.Geometry#getNormal(Point)} method.
     * Returns the normal vector of the plane at any given point (which is constant).
     *
     * @param vertex A {@link Point} object representing a point on the plane.
     *               This parameter may be unused in the implementation of this method.
     * @return The normal vector as a {@link Vector} object.
     */
    @Override
    public Vector getNormal(Point vertex) {
        return normal;
    }

    /**
     * Finds the intersection points between a ray and the plane.
     * Since a plane is infinite, it may intersect the ray at one point or infinitely many points.
     * In this implementation, it returns null since it's not yet implemented.
     *
     * @param ray The ray to intersect with the plane.
     * @return A list of intersection points between the ray and the plane, or null if there are no intersections.
     */
    @Override

    public List<Point> findIntersections(Ray ray){
        //if the ray starts at the plane
        if(ray.getHead().equals(q)){
            return null;
        }

        double t=alignZero(normal.dotProduct(ray.getDirection()));
        //if the ray is parallel to the plane
        if(isZero(t)){
            return null;
        }

        double t1=alignZero(normal.dotProduct(q.subtract(ray.getHead()))/t);
        //if the ray is in the opposite direction of the normal
        if(t1<=0){
            return null;
        }
        //if the ray intersects the plane
        return List.of(ray.getPoint(t1));
    }
}
