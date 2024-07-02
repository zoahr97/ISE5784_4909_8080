package lighting;

import primitives.Color;
import primitives.Point;

/**
 * Light is an abstract class representing a light source in a scene.
 * It holds the intensity of the light.
 */
abstract class Light {
    protected Color intensity;

    /**
     * Constructs a new Light with the specified intensity.
     *
     * @param intensity the color intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }

    /**
     * Abstract method to get the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is calculated
     * @return the color intensity of the light at the given point
     */
    public abstract Color getIntensity(Point p);

}
