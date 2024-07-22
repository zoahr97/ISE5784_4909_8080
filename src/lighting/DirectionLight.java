package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionLight class represents a directional light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionLight extends Light implements LightSource {
    /**
     * The direction vector for this geometric shape or ray.
     * This vector typically represents the direction in which the shape extends
     */
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

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
