package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
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
     * or null if there are no intersections.
     */

    @Override


    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead(); // The starting point of the ray
        Vector v = ray.getDirection(); // The direction vector of the ray
        Plane plane = this.plane;

        if (plane.findGeoIntersectionsHelper(ray) == null)
            return null;

        Point intersectionPoint = plane.findGeoIntersectionsHelper(ray).getFirst().point;

        // Vectors from the ray's starting point to the vertices of the triangle
        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector v3 = this.vertices.get(2).subtract(p0);

        // Normals to the planes formed by the ray and the triangle's edges
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Dot products of the ray's direction with the normals
        double d1 = alignZero(v.dotProduct(n1));
        double d2 = alignZero(v.dotProduct(n2));
        double d3 = alignZero(v.dotProduct(n3));

        // Check if the ray intersects the triangle
        // The ray intersects the triangle if all the dot products are either positive or negative
        if ((d1 > 0 && d2 > 0 && d3 > 0) || (d1 < 0 && d2 < 0 && d3 < 0)) {
            // If the ray intersects the triangle, find the intersection points with the plane
            return List.of(new GeoPoint(this, intersectionPoint));
        }
        // If the ray does not intersect the triangle, return null
        return null;
    }
}
