package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionLight class represents a directional light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructs a new DirectionLight with the specified intensity and direction.
     *
     * @param intensity the color intensity of the light
     * @param direction the direction of the light
     */
    public DirectionLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Gets the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    /**
     * Gets the direction of the light from the light source to a given point.
     *
     * @param p the point to which the light direction is calculated
     * @return the direction vector from the light source to the given point
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
