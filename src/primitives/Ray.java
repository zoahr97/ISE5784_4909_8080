package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 *This class represents a ray represented by both a point and a vector
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Ray {
    /** field of the start of the ray
     */
    private Point head;
    /**field of the direction(vector) of the ray
     */
    private Vector direction;

    private static final double DELTA = 0.1;
    /**
     * ray constructor gets point and vector
     * @param p1 is point to start from her
     * @param v1 is the direction of the point
     */
    public Ray(Point p1,Vector v1){
        head = p1;
        direction =v1.normalize();
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
        return ob instanceof Ray && ((Ray)ob).head.equals(head)&&
                (((Ray)ob).direction.equals(direction));
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
    public Point getPoint(double t){
        if(isZero(t)){
            return head;
        }
        return head.add(direction.scale(t));
    }


    public Ray get(int i) {
        return new Ray(head.add(direction.scale(i)), direction.scale(i));
    }
/**
//   Finds the closest point to the ray's head from a list of points.
//   @param points the list of points to check.
//   @return the point closest to the ray's head, or null if the list is empty.
*/
     public Point findClosestPoint(List<Point> points)
    {

        if (points == null || points.isEmpty()) {
            return null;
        }

        Point closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (Point point : points) {
            double distance = head.distance(point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

}
