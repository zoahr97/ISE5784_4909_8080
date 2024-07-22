package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * LightSource is an interface representing a light source in a scene.
 * It provides methods to get the intensity and direction of the light at a given point.
 */
public interface LightSource {
    /**
     * Gets the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction of the light from the light source to a given point.
     *
     * @param p the point to which the light direction is calculated
     * @return the direction vector from the light source to the given point
     */
    public Vector getL(Point p);

    /**
     * Calculates the distance between this geometric shape and a given point.
     * This method determines the shortest distance from the point to the closest part of the shape.
     *
     * @param point the point from which to calculate the distance to the geometric shape
     * @return the distance from the given point to the geometric shape
     * @throws IllegalArgumentException if the point is null
     */
    double getDistance(Point point);

}
