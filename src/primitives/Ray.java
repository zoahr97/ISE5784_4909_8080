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

    /**
     *
     * @param p1 is point to start from her
     * @param v1 is the direction of the point
     */
    public Ray(Point p1,Vector v1){
        head = p1;
        direction =v1.normalize();
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) return true;
        return ob instanceof Ray && ((Ray)ob).head.equals(head)&&(((Ray)ob).direction.equals(direction));
    }


    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
