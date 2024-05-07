package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    protected Ray axis;

    public Tube(double radius) {
        super(radius);
    }

    public Vector getNormal(Point p){
        return null;
    }
}
