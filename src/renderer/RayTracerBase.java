package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracerBase is an abstract class that represents the base for all ray tracing operations.
 * It provides a common interface for tracing rays through a scene and computing the color they produce.
 */
public abstract class RayTracerBase {
    protected Scene scene; // The scene to trace rays through

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene the scene to trace rays through
     */
    RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray through the scene and computes the color it produces.
     * This method must be implemented by subclasses to provide specific ray tracing behavior.
     *
     * @param ray the ray to trace
     * @return the color produced by tracing the ray
     */
    public abstract Color traceRay(Ray ray);
}
