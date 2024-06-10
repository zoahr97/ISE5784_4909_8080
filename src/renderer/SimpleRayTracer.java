package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.BLACK;
import static primitives.Util.alignZero;

public class SimpleRayTracer extends RayTracerBase {

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Point closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            return scene.background;

        return null;
    }


    @Override
    public Color TraceRays(List<Ray> rays) {
        Color color = new Color(BLACK);
        for (Ray ray : rays) {
            Point clossestGeoPoint = findClosestIntersection(ray);
            if (clossestGeoPoint == null)
                color = color.add(scene.background);
            else color = null;
        }
        return color.reduce(rays.size());
    }



    private Point findClosestIntersection(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null)
            return null;
        //returns closest point
        return ray.findClosestPoint(intersections);
    }

}
