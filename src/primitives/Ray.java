package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class represents a ray represented by both a point and a vector
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Ray {
    private static final double DELTA = 0.1;
    /**
     * field of the start of the ray
     */
    private Point head;
    /**
     * field of the direction(vector) of the ray
     */
    private Vector direction;

    /**
     * ray constructor gets point and vector
     *
     * @param p1 is point to start from her
     * @param v1 is the direction of the point

     */
    public Ray(Point p1, Vector v1) {
        head = p1;
        direction = v1.normalize();
    }
    /**
     * Constructs a new Ray with an offset to avoid self-intersection.
     *
     * @param p1     The starting point of the ray.
     * @param v1     The direction of the ray.
     * @param normal The normal at the point of intersection.

     */
    public Ray(Point p1, Vector v1, Vector normal) {
        direction = v1.normalize();
        double offset = normal.dotProduct(v1) > 0 ? DELTA : -DELTA;
        head = p1.add(normal.scale(offset));
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) return true;
        return ob instanceof Ray && ((Ray) ob).head.equals(head) &&
                (((Ray) ob).direction.equals(direction));
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Computes the point along the ray at a given distance from its head.
     *
     * @param t The distance along the ray from its head.
     * @return The point on the ray at the specified distance.
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
    }



    /**
     * //   Finds the closest point to the ray's head from a list of points.
     * //   @param points the list of points to check.
     * //   @return the point closest to the ray's head, or null if the list is empty.
     */


    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (GeoPoint geoPoint : points) {
            double distance = head.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }

        return closestPoint;
    }

    /**
     * Finds the closest point to the origin among the given list of points.
     * Returns null if the list is null or empty.
     *
     * @param points A list of points to search from.
     * @return The closest point to the origin, or null if the list is empty or null.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

}


