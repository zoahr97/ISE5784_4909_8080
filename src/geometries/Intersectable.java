package geometries;

import primitives.*;

import java.util.List;

/**
 * The Intersectable interface represents geometric objects that can be intersected by a ray.
 * Implementing classes must provide a method to find intersection points with a given ray.
 * This interface provides a contract for objects that can participate in ray intersection calculations.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public interface Intersectable {

    /**
     * Finds intersection points between the intersectable object and a given ray.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points between the ray and the object, or an empty list if there are no intersections.
     */
    List<Point> findIntsersections(Ray ray);

}

