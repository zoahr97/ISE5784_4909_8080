package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * The Scene class represents a scene in a 3D environment, containing geometries, ambient light, background color, and a name.
 */
public class Scene {
    public String name; // The name of the scene
    public Color background = Color.BLACK; // The background color of the scene, default is black
    public AmbientLight ambientLight = AmbientLight.NONE; // The ambient light of the scene, default is none
    public Geometries geometries = new Geometries(); // The geometries in the scene

    /**
     * Constructs a Scene with a given name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the ambient light of the scene
     * @return the current Scene instance
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries the geometries in the scene
     * @return the current Scene instance
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param color the background color of the scene
     * @return the current Scene instance
     */
    public Scene setBackground(Color color) {
        this.background = color;
        return this;
    }
}
