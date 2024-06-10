package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    // שדה עצמת תאורת מילוי
    private  Color intensity=Color.BLACK;
    /**
     * construct the ambient light using a color, and it's attenuation factor with dad constructor.
     * @param Ia the base intensity of the light
     * @param Ka the attenuation factor of the intensity for each rgb color
     */
    public AmbientLight(Color Ia, Double3 Ka) {
      intensity =  Ia.scale(Ka);
    }
    public AmbientLight(Color Ia, Double Ka) {
        intensity =  Ia.scale(Ka);
    }
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public Color getIntensity() {
        return intensity;
    }

}
