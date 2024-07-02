package geometries;

/**
 * An abstract class representing a radial geometry shape. Radial geometry shapes have a single radius value that
 * determines their size.
 *
 * @author Dvora Enav and Zohar Tamsut
 **/

public abstract class RadialGeometry extends Geometry {
    protected final double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }


}
