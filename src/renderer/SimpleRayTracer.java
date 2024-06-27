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

    /**
     * Traces a ray and calculates the color of the closest intersection point.
     *
     * @param ray the ray to be traced
     * @return the color of the closest intersection point or the background color if no intersection is found
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return CalcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at the closest intersection point.
     *
     * @param closestPoint the closest intersection point
     * @param ray          the ray that intersects the geometry
     * @return the color at the closest intersection point
     */
    private Color CalcColor(GeoPoint closestPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(closestPoint, ray));
    }

    /**
     * Calculates the diffusive reflection of the light.
     *
     * @param material  the material of the geometry
     * @param nl        the dot product of the normal and light direction vectors
     * @param intensity the intensity of the light
     * @return the color of the diffusive reflection
     */
    private Color calcDiffusive(Material material, double nl, Color intensity) {
        return intensity.scale(material.kD.scale(Math.abs(nl)));
    }

    /**
     * Calculates the specular reflection of the light.
     *
     * @param material  the material of the geometry
     * @param n         the normal vector at the point
     * @param shininess the shininess coefficient of the material
     * @param nl        the dot product of the normal and light direction vectors
     * @param v         the direction vector of the ray
     * @param l         the direction vector of the light
     * @param intensity the intensity of the light
     * @return the color of the specular reflection
     */
    private Color calcSpecular(Material material, Vector n, int shininess, double nl, Vector v, Vector l, Color intensity) {
        Vector r = l.subtract(n.scale(2 * nl)).normalize();
        double vr = Math.max(0, -v.dotProduct(r));
        return intensity.scale(material.kS.scale(Math.pow(vr, material.shininess)));
    }

    /**
     * Calculates the local effects of the light at the given intersection point.
     *
     * @param gp  the intersection point
     * @param ray the ray that intersects the geometry
     * @return the color at the given intersection point considering local effects of the light
     */
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
                color = color.add(calcDiffusive(material, nl, iL)).add(
                        calcSpecular(material, n, material.shininess, nl, v, l, iL));
            }
//
        }

        return color;
    }
}
