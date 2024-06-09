package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient lighting in a scene.
 * It defines the ambient light's intensity based on initial intensity and a scaling factor.
 */
public class AmbientLight {
    private final Color intensity;

    /**
     * A constant representing no ambient light.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an AmbientLight with given intensity and scaling factor.
     *
     * @param Ia the initial intensity of the ambient light
     * @param ka the scaling factor (as Double3) for the intensity
     */
    public AmbientLight(Color Ia, Double3 ka) {
        this.intensity = Ia.scale(ka);
    }

    /**
     * Constructs an AmbientLight with given intensity and scaling factor.
     *
     * @param Ia the initial intensity of the ambient light
     * @param ka the scaling factor (as double) for the intensity
     */
    AmbientLight(Color Ia, double ka) {
        this.intensity = Ia.scale(ka);
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
