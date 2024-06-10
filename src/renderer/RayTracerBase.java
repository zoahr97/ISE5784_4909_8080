package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 *  represents a ray tracer
 *  1) traces rays through a scene
 *  2) finding a color of an object that intersects closest to the ray
 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Tracing a ray through a scene and finding the color of the object closest to the head of the ray
     * @param ray The ray to trace the scene with
     * @return The color of the object the ray 'sees' first
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     * @param rays the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    public abstract Color TraceRays(List<Ray> rays);


}
