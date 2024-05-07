package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    private Point center;
    public Sphere(Point center,double radius) {
        super(radius);
        this.center = center;
    }
    public Vector getNormal(Point p){
        return null;
    }

}
