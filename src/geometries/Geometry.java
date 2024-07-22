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
    /**
     * The emission color of the geometric shape.
     * Initialized to black by default.
     */
    protected Color emission = new Color(java.awt.Color.BLACK);

    /**
     * The material properties of the geometric shape.
     * Initialized with default values.
     */
    private Material material = new Material();


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
    /**
     * Calculates the normal vector to the surface at a given point.
     * This method is abstract and must be implemented by subclasses
     * to define the specific normal vector calculation for different geometric shapes.
     *
     * @param p the point on the surface where the normal vector is to be calculated
     * @return the normal vector at the given point
     * @throws IllegalArgumentException if the point does not lie on the surface
     */
    public abstract Vector getNormal(Point p);




}
