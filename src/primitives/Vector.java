package primitives;

/**
 * This class represents a vector
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Vector extends Point {
    /**
     * vector constructor that get  3 doubles
     * @param x is first coordinate
     * @param y is second coordinate
     * @param z is third coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) throw new IllegalArgumentException(" can not create the zero vector");
    }

    /**
     *  vector constructor that get double 3 parameter
     * @param xyz is point of double3 class
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) throw new IllegalArgumentException(" can not create the zero vector");
    }

    /**
     * this method takes two vectors and connect them to a one vector
     * @param v is the vector we add to the second vector
     * @return the new vector after the add
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * This method takes a vector and multiplies each of its coordinates by a scalar
     * @return the new vector after we multiplied every coordinate in the scalar
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     *  this method Calculates the length of the vector squared
     * @return the length of the vector after square
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * this method calculates the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * this method does dot product between two vectors
     * @param v is the vector we do the dot product with him
     * @return the  result of the dot product and is a scalar
     */
    public double dotProduct(Vector v) {
        return  (xyz.d1 * v.xyz.d1) +
                (xyz.d2 * v.xyz.d2) +
                (xyz.d3 * v.xyz.d3);
    }

    /**
     * this method is takes two vectors and does a cross product between them.
     * @param vector One of the vectors on which the cross product is done
     * @return A new vector perpendicular to the two existing vectors
     */

    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1);
    }

    /**
     * this method takes vector and change his length to be 1(normalizing)
     * @return a new vector that is normalized by divide all coordinate in the length of the vector
     */
    public Vector normalize() {
        double len = this.length();
        return new Vector(
                this.xyz.d1 / len,
                this.xyz.d2 / len,
                this.xyz.d3 / len);
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