package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

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
    /** for supersampling.
     * Traces a list of rays through the scene to determine the color at the intersection points.
     *
     * @param rays the list of rays to be traced through the scene.
     * @return the color resulting from tracing the given rays, which may be an average or a combined color
     *         depending on the implementation.
     * @throws NullPointerException if the provided rays list is null.
     * @throws IllegalArgumentException if the rays list is empty.
     */
    public abstract Color traceRays(List<Ray> rays);

}
