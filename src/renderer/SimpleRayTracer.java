package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * SimpleRayTracer is a class that extends RayTracerBase and provides basic
 * ray tracing functionality for rendering a scene.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene the scene to be rendered by the ray tracer
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }
    /**
     * Calculates the color at a given point in the scene.
     * This method currently returns the intensity of the ambient light in the scene.
     *
     * @param p the point at which to calculate the color
     * @return the color at the given point, which is the intensity of the ambient light
     */
    private Color CalcColor(Point p) {
       return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections==null)
            return scene.background;
        Point closetPoint = ray.findClosestPoint(intersections);
        return CalcColor(closetPoint);

    }
}
