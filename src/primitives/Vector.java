package primitives;

/**
 * This class represents a three-dimensional vector in Euclidean space.
 * It inherits from the Point class to leverage its properties.
 * Each vector consists of three coordinates (x, y, z).
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Vector extends Point {

    /**
     * Constructs a vector from three double values representing its coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException if attempting to create the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Cannot create the zero vector.");
    }

    /**
     * Constructs a vector from a Double3 object containing its coordinates.
     *
     * @param xyz The Double3 object containing the coordinates of the vector.
     * @throws IllegalArgumentException if attempting to create the zero vector.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Cannot create the zero vector.");
    }

    /**
     * Adds another vector to this vector.
     *
     * @param v The vector to add to this vector.
     * @return A new vector representing the sum of this vector and the given vector.
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * Scales this vector by a scalar value.
     *
     * @param scalar The scalar value to multiply each coordinate of this vector by.
     * @return A new vector representing this vector scaled by the given scalar.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Calculates the squared length of this vector.
     *
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculates the length of this vector.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param v The vector to compute the dot product with.
     * @return The dot product of this vector with the given vector.
     */
    public double dotProduct(Vector v) {
        return (xyz.d1 * v.xyz.d1) +
                (xyz.d2 * v.xyz.d2) +
                (xyz.d3 * v.xyz.d3);
    }

    /**
     * Computes the cross product of this vector with another vector.
     *
     * @param vector The vector to compute the cross product with.
     * @return A new vector representing the cross product of this vector with the given vector.
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }

    /**
     * Normalizes this vector, i.e., changes its length to 1.
     *
     * @return A new vector representing this vector normalized to unit length.
     */
    public Vector normalize() {
        double len = this.length();
        return new Vector(this.xyz.reduce(len));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }
}