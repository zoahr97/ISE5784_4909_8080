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

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
