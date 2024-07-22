package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;


/**
 * The SpotLight class represents a spotlight in a scene.
 * It extends the PointLight class.
 */
public class SpotLight extends PointLight {
    private Vector direction;
    private double narrowBeam = 1;

    /**
     * Constructs a new SpotLight with the specified intensity and position.
     *
     * @param intensity the color intensity of the light
     * @param position  the position of the light source
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the narrow beam angle for the spotlight.
     *
     * @param narrowBeam the narrow beam angle in degrees.
     * @return the current SpotLight instance with the updated narrow beam angle.
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Sets the constant attenuation factor (kC) for the spotlight.
     *
     * @param kC the constant attenuation factor
     * @return the updated SpotLight object
     */
    public SpotLight setKC(double kC) {
        super.setKC(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor (kL) for the spotlight.
     *
     * @param kL the linear attenuation factor
     * @return the updated SpotLight object
     */
    public SpotLight setKL(double kL) {
        super.setKL(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor (kQ) for the spotlight.
     *
     * @param kQ the quadratic attenuation factor
     * @return the updated SpotLight object
     */
    public SpotLight setKQ(double kQ) {
        super.setKQ(kQ);
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double a = alignZero(direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(Math.max(0, Math.pow(a, narrowBeam)));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    @Override
    public double getDistance(Point point) {
        return super.getDistance(point);
    }
}


