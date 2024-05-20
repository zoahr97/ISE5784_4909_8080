package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Represents a triangle in 3D space defined by three {@link Point}s.
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Triangle extends Polygon {
    /**
     Constructs a triangle object from three points.
     @param a The first point of the triangle.
     @param b The second point of the triangle.
     @param c The third point of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
