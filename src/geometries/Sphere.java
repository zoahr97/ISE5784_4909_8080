package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Sphere class which represents the location of a Sphere in space
 *  @author Dvora Enav and Zohar Tamsut
 */

public class Sphere extends RadialGeometry{
    /**
     * The center point of the sphere.
     */
    private Point center;
    /**
     * Constructor for a sphere object.
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center,double radius) {
        super(radius);
        this.center = center;
    }
    /**
     * Calculates and returns the normal vector to the surface of the sphere at a given point.
     *
     * @param point The point on the surface of the sphere at which to calculate the normal vector.
     * @return The normal vector as a {@link Vector} object.
     */

    public Vector getNormal(Point point) {
        // Calculate the vector from the center of the sphere to the given point
        //  and Normalize the vector to obtain the normal vector
        return point.subtract(center).normalize();
    }



    public List<Point> findIntersections(Ray ray) {
        // נקודת ההתחלה של הקרן
        Point p0 = ray.getHead();
        // כיוון הקרן
        Vector v = ray.getDirection();

        // וקטור ממרכז הכדור לנקודת ההתחלה של הקרן
        Vector u;
        try {
            u = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            // אם נקודת ההתחלה של הקרן תואמת למרכז הכדור, מחזירים את נקודת החיתוך היחידה
            return List.of(ray.getPoint(radius));
        }

        // חישוב המרחק של נקודת ההתחלה של הקרן ממרכז הכדור על ציר הקרן
        double tm = u.dotProduct(v);
        // מרחק בריבוע מהקרן למרכז הכדור
        double dSquared = u.lengthSquared() - tm * tm;
        // רדיוס הכדור בריבוע
        double radiusSquared = radius * radius;

        // אם המרחק גדול מרדיוס הכדור, אין חיתוך
        if (dSquared > radiusSquared) {
            return null;
        }

        // חישוב נקודות החיתוך
        double th = Math.sqrt(radiusSquared - dSquared);
        double t1 = tm - th;
        double t2 = tm + th;

        List<Point> intersections = new LinkedList<>();

        // נקודת החיתוך הראשונה
        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            intersections.add(p1);
        }

        // נקודת החיתוך השנייה
        if (t2 > 0 && !isZero(t1 - t2)) {
            Point p2 = ray.getPoint(t2);
            intersections.add(p2);
        }

        return intersections.isEmpty() ? null : intersections;
    }
}