package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 * Implementing classes must provide a method to find intersection points with a given ray.
 * This interface provides a contract for objects that can participate in ray intersection calculations.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public abstract class Intersectable {

    /**
     * Finds intersection points between the intersectable object and a given ray.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points between the ray and the object, or an empty list if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points (as GeoPoints) between the given ray and the geometric shape.
     * This method serves as a public interface to find intersections, internally delegating the work
     * to the abstract helper method {@link #findGeoIntersectionsHelper(Ray)}.
     *
     * @param ray the ray for which to find intersections with the geometric shape
     * @return a list of GeoPoints representing the intersection points, or null if there are no intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find the intersection points (as GeoPoints) between the given ray and the geometric shape.
     * This method is abstract and must be implemented by subclasses to define the specific intersection
     * calculation for different geometric shapes.
     *
     * @param ray the ray for which to find intersections with the geometric shape
     * @return a list of GeoPoints representing the intersection points, or null if there are no intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);



    /**
     * GeoPoint represents a point on a geometric shape, including the specific geometry
     * and the coordinates of the point.
     */
    public static class GeoPoint {
        /**
         * The geometry on which the point lies.
         */
        public Geometry geometry;

        /**
         * The coordinates of the point.
         */
        public Point point;

        /**
         * Constructs a GeoPoint with the specified geometry and point.
         *
         * @param geometry The geometry on which the point lies.
         * @param point    The coordinates of the point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object ob) {
            if (this == ob) return true;
            if (!(ob instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}











