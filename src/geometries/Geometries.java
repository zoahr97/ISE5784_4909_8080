package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * The Geometries class represents a collection of geometric objects.
 * It implements the Intersectable interface, allowing it to find intersections
 * with a given ray.
 */
public class Geometries implements Intersectable {

    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * Default constructor, initializes an empty list of geometries.
     */
    public Geometries() {}

    /**
     * Constructor that initializes the list of geometries with the provided elements.
     *
     * @param geometries the geometries to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds the provided geometries to the collection.
     *
     * @param geometries the geometries to add to the collection
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * Finds the intersections of a given ray with all the geometries in the collection.
     *
     * @param ray the ray to intersect with the geometries
     * @return a list of intersection points, or null if no intersections are found
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (Intersectable geometry : geometries) {
            List<Point> i = geometry.findIntersections(ray);
            if (i != null) {
                if (intersections == null) {
                    intersections = new ArrayList<>();
                }
                intersections.addAll(i);
            }
        }
        return intersections;
    }
}
