package primitives;

/**
 * This class represents a point in three-dimensional space.
 * Each point is defined by its x, y, and z coordinates.
 * The coordinates are stored using the Double3 class for encapsulation.
 * Instances of this class are immutable.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Point {

    /**
     * The coordinates of the point stored as a Double3 object.
     */
    final protected Double3 xyz;

    /**
     * Represents the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * Constructs a point from three double values representing its coordinates.
     *
     * @param d1 The x-coordinate of the point.
     * @param d2 The y-coordinate of the point.
     * @param d3 The z-coordinate of the point.
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a point from a Double3 object containing its coordinates.
     *
     * @param xyz The Double3 object containing the coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Retrieves the X coordinate from a 3D point represented by a Double3 object.
     *
     * @param xyz The Double3 object representing the 3D point.
     * @return The X coordinate of the point.
     */
    public double getX(Double3 xyz) {
        return xyz.d1;
    }

    /**
     * Retrieves the Y coordinate from a 3D point represented by a Double3 object.
     *
     * @param xyz The Double3 object representing the 3D point.
     * @return The Y coordinate of the point.
     */
    public double getY(Double3 xyz) {
        return xyz.d2;
    }

    /**
     * Retrieves the Z coordinate from a 3D point represented by a Double3 object.
     *
     * @param xyz The Double3 object representing the 3D point.
     * @return The Z coordinate of the point.
     */
    public double getZ(Double3 xyz) {
        return xyz.d3;
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
     * Returns the x-coordinate of this point.
     *
     * @return The x-coordinate of this point.
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return The y-coordinate of this point.
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Returns the z-coordinate of this point.
     *
     * @return The z-coordinate of this point.
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     *
     * @param v The vector to add to this point.
     * @return A new point resulting from the addition of the given vector to this point.
     */
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param point The point to calculate the squared distance to.
     * @return The squared distance between this point and the given point.
     */
    public double distanceSquared(Point point) {
        return ((xyz.d1 - point.xyz.d1) * (xyz.d1 - point.xyz.d1)) +
                ((xyz.d2 - point.xyz.d2) * (xyz.d2 - point.xyz.d2)) +
                ((xyz.d3 - point.xyz.d3) * (xyz.d3 - point.xyz.d3));
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param point The point to calculate the distance to.
     * @return The distance between this point and the given point.
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Computes the vector from another point to this point.
     *
     * @param point The point to compute the vector from.
     * @return A vector representing the displacement from the given point to this point.
     */
    public Vector subtract(Point point) {
       // if(xyz==point.xyz)
            //throw new IllegalArgumentException("Zero vector not allowed");
        return new Vector(xyz.subtract(point.xyz));

    }
}
