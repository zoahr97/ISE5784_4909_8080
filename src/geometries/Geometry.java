package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Define an interface named Geometry for some geometric body.
 *
 * @author Dvora Enav and Zohar Tamsut
 */
public abstract class Geometry extends Intersectable {
    protected Color emission=new Color(java.awt.Color.BLACK);
    private Material material=new Material();

    /**
     * Gets the material of the object.
     *
     * @return the material of the object.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of this geometry.
     *
     * @param material the material to set
     * @return this geometry instance with the updated material
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the emission color of the geometric shape.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point p);


}
