package lighting;

import primitives.Color;

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
}
