package primitives;

/**
 * This class represents a point in space with 3 coordinates represented by a field of the class double3 by containment
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Point {
    /**field of point of 3 coordinators
     */
    final protected Double3 xyz;
    /**
     * the zero point
     */
    public static final Point ZERO= new Point(Double3.ZERO);
    /**
     * @param d1 is first coordinate of point
     * @param d2 is  second coordinate of point
     * @param d3 is third coordinate of point
     */
    public Point(double d1, double d2,double d3) {
        this.xyz = new Double3(d1,d2,d3);

    }

    /**
     * @param xyz is point of 3 coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }
    @Override
    public String toString() {
        return "" + xyz;
    }

    /**
     * @param v is the vector we add to the point
     */

    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    /**
     *
     * @param p is point we want to calculate the distance in square between her and xyz
     * @return the distance in square between the two points
     */
    public double distanceSquared(Point p){
        return ((xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1))+
                ((xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2))+
                ((xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3));
    }

    /**
     *
     * @param p1 is point we want to calculate the distance between her and xyz(the second point)
     * @return the distance between the two points
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }

    /**
     *
     * @param p1 is the second point we start the vector from her
     * @return vector from p1 to xyz
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }
}
