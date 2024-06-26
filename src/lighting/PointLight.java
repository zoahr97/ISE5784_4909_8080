package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {
    private double narrowBeam = 1;
    protected Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
    /**
     * Sets the constant attenuation factor (kC) for the point light.
     *
     * @param kC the constant attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor (kL) for the point light.
     *
     * @param kL the linear attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor (kQ) for the point light.
     *
     * @param kQ the quadratic attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Constructs a new PointLight with the specified intensity and position.
     *
     * @param intensity the color intensity of the light
     * @param position the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Gets the intensity of the light at a given point.
     *
     * @param p the point at which the intensity is calculated
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return  intensity.scale( 1/(( (kC + kL* position.distance(p))+kQ* position.distanceSquared(p))));
    }

    /**
     * Gets the direction of the light from the light source to a given point.
     *
     * @param p the point to which the light direction is calculated
     * @return the direction vector from the light source to the given point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }


}
