package primitives;

/**
 * The Material class represents the material properties of an object,
 * including its diffuse and specular reflection coefficients and its shininess.
 */
public class Material {

    /**
     * The transparency coefficient of the material.
     * It determines how much light is transmitted through the material.
     * The default value is {@link Double3#ZERO}, which means no transparency.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material.
     * It determines how much light is reflected off the material's surface.
     * The default value is {@link Double3#ZERO}, which means no reflection.
     */
    public Double3 kR = Double3.ZERO;

    /**
     * The shininess coefficient of the material.
     * It determines how sharp or diffused the specular highlights on the material's surface are.
     * A higher value results in sharper and smaller highlights, giving the material a more polished look.
     * The default value is 0, which means no specular highlight.
     */
    public int shininess = 0;

    /**
     * The diffuse reflection coefficient of the material.
     * It determines how much light is diffusely reflected from the material's surface.
     * The default value is {@link Double3#ZERO}, which means no diffuse reflection.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular reflection coefficient of the material.
     * It determines how much light is reflected in a mirror-like manner from the material's surface.
     * The default value is {@link Double3#ZERO}, which means no specular reflection.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Sets the transparency coefficient of the material.
     *
     * @param kT the transparency coefficient to set
     * @return the current instance of the material for chaining
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * Sets the transparency coefficient of the material.
     *
     * @param kT the transparency coefficient to set
     * @return the current instance of the material for chaining
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR the reflection coefficient to set
     * @return the current instance of the material for chaining
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    /**
     * Sets the reflection coefficient (kR) for the material.
     *
     * @param kR The reflection coefficient value to set.
     * @return This Material object with the updated reflection coefficient.
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) with a Double3 value.
     *
     * @param kD the diffuse reflection coefficient
     * @return the updated Material object
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) with a double value.
     *
     * @param kD the diffuse reflection coefficient
     * @return the updated Material object
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) with a Double3 value.
     *
     * @param kS the specular reflection coefficient
     * @return the updated Material object
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) with a double value.
     *
     * @param kS the specular reflection coefficient
     * @return the updated Material object
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param shininess the shininess value
     * @return the updated Material object
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}
