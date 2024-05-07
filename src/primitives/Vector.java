package primitives;

/**
 * This class represents a vector
 */
public class Vector extends Point {
    /**
     *
     * @param x is first coordinate
     * @param y is second coordinate
     * @param z is third coordinate
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException(" can not create the zero vector");
    }
    /**
     * @param d is point of double3 class
     */
    public Vector(Double3 d) {
        super(d);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException(" can not create the zero vector");
    }

    /**
     *
     * @param v is the vector we add to the second vector
     * @return the new vector after the add
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }
    /**
     * @return the new vector after we multiplied every coordinate in the scalar
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     *
     * @return the length of the vector after square
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *
     * @param v is the vector we do the dot product whis him
     * @return the dot result of the dot product(number)
     */
    public double dotProduct(Vector v) {
        return (xyz.d1*v.xyz.d1)+ (xyz.d2*v.xyz.d2)+(xyz.d3*v.xyz.d3);
    }

    /**
     *
     * @param v One of the vectors on which the cross product is done
     * @return A new vector perpendicular to the two existing vectors
     */
    //public Vector crossProduct(Vector v) {
     //   return new Vector(xyz.d2*v.xyz.d3-xyz.d3*v.xyz.d2,xyz.d3*xyz.d1*v.xyz.d3,xyz.d1*v.xyz.d2-xyz.d2*v.xyz.d1);
   //}
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }
    /**
     *
     * @return a new vector that is normalized by divide all coordinate in the length of the vector
     */
    public Vector normalize() {
        return  new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }
    @Override
    public String toString() { return "->" + super.toString(); }
}
