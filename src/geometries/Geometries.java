package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

public class Geometries  implements Intersectable{
   private final List<Intersectable> intersectables=new LinkedList<Intersectable>();

    public Geometries() {

    }

    public Geometries(Intersectable... intersectables) {
       add(intersectables);
    }

    public void add(Intersectable... intersectables) {
        Collections.addAll(this.intersectables, intersectables);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        List<Point> allPoints=new LinkedList<Point>();
        for (Object geometry : intersectables) {
            List<Point> points = findIntersectionPoints(geometry, ray);
            if (points != null && !points.isEmpty()) {
                for (Point point : points) {
                    allPoints.addAll(points);
                }
            }
        }

        return allPoints.isEmpty() ? null : allPoints;
    }

    public static List<Point> findIntersectionPoints(Object geometry, Ray ray) {
        if (geometry instanceof Sphere) {
            List<Point> points = ((Sphere) geometry).findIntersections(ray);
            return points != null && !points.isEmpty() ? points : null;
        } else if (geometry instanceof Plane) {
            List<Point> points = ((Plane) geometry).findIntersections(ray);
            return points != null && !points.isEmpty() ? points : null;
        } else if (geometry instanceof Triangle) {
            List<Point> points = ((Triangle) geometry).findIntersections(ray);
            return points != null && !points.isEmpty() ? points : null;
        } else {
            return null;
        }
    }

}
