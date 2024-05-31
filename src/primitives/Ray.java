package primitives;

import java.util.Objects;

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
    public Point getPoint(double t) {

        return head.add(direction.scale(t));
    }


    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    public Point getPoint(double radius)
    {
        return getHead().add(getDirection().scale(radius));
    }
}
