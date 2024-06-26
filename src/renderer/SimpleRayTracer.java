package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.alignZero;

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

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        Intersectable.GeoPoint closetPoint = ray.findClosestGeoPoint(intersections);
        return CalcColor(closetPoint, ray);
    }

    private Color CalcColor(GeoPoint closetPoint, Ray ray) {

        return scene.ambientLight.getIntensity().add(calcLocalEffects(closetPoint, ray));
    }

    private Color calcDiffusive(Material material, double nl, Color intensity) {
        return intensity.scale(material.kD.scale(Math.abs(nl)));
    }

    private Color calcSpecular(Material material, Vector n, int shininess, double nl, Vector v, Vector l, Color intensity) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize();
        double vr = -v.dotProduct(r);
        vr = Math.max(0, vr);
        return intensity.scale(material.kS.scale(Math.pow(vr, material.shininess)));

    }


    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection().normalize();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point).normalize();
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {  // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                Color diffusive = calcDiffusive(material, nl, iL);
                Color specular = calcSpecular(material, n, material.shininess, nl, v, l, iL);
                color = color.add(diffusive).add(specular);
            }
        }
        return color;
    }
}

/**
 * Calculates the color at a given point in the scene.
 * This method currently returns the intensity of the ambient light in the scene.
 *
 * @param geoPoint the point of the specific geometry at which to calculate the color
 * @param ray
 * @return the color at the given point, which is the intensity of the ambient light
 */







