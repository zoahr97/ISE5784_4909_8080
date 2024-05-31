package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a triangle in 3D space defined by three {@link Point}s.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public class Triangle extends Polygon {
    /**
     * Constructs a triangle object from three points.
     *
     * @param a The first point of the triangle.
     * @param b The second point of the triangle.
     * @param c The third point of the triangle.
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }


    public List<Point> findIntsersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        //if there are no intersections with the plane, there are no intersections with the triangle
        if (intersections == null) {
            return null;
        }

        //if the ray intersects the plane at the triangle's plane
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double s1 = ray.getDirection().dotProduct(n1);
        double s2 = ray.getDirection().dotProduct(n2);
        double s3 = ray.getDirection().dotProduct(n3);

        //if the ray is parallel to the triangle's plane
        if (isZero(s1) || isZero(s2) || isZero(s3)) {
            return null;
        }

        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0) {
            return List.of(intersections.get(0));

        }
        //if the ray intersects the plane but not the triangle
        return null;
    }
}
