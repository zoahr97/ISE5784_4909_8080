package primitives;

/**
 * The Material class represents the material properties of an object,
 * including its diffuse and specular reflection coefficients and its shininess.
 */
public class Material {
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int shininess = 0;

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
