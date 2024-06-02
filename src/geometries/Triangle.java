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
    /**
     * Finds the intersection points between a given ray and the triangle.
     * This method uses the Möller–Trumbore intersection algorithm to determine
     * if and where a ray intersects a triangle in 3D space.
     *
     * @param ray the ray to intersect with the triangle.
     * @return a list of intersection points if the ray intersects the triangle,
     *         or null if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        // Find intersections with the plane containing the triangle
        List<Point> intersections = plane.findIntersections(ray);
        // If there are no intersections with the plane, return null
        if (intersections == null) {
            return null;
        }

        // Calculate vectors from the ray's head to each of the triangle's vertices
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        // Calculate normal vectors to the triangle's edges
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate dot products of the ray's direction with the normal vectors
        double s1 = ray.getDirection().dotProduct(n1);
        double s2 = ray.getDirection().dotProduct(n2);
        double s3 = ray.getDirection().dotProduct(n3);

        // If the ray is parallel to any of the triangle's planes
        if (isZero(s1) || isZero(s2) || isZero(s3)) {
            return null;
        }

        // Check if the intersection point is inside the triangle
        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            return List.of(intersections.get(0));
        }

        // If the ray intersects the plane but not the triangle
        return null;
    }


}
