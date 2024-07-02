package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * SimpleRayTracer is a class that extends RayTracerBase and provides basic
 * ray tracing functionality for rendering a scene.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * A small constant used to offset rays to avoid numerical issues such as self-intersection.
     * This value is crucial in ray tracing algorithms to prevent a ray from incorrectly
     * intersecting the surface it originates from due to floating-point precision errors.
     */
    private static final double EPS = 0.1;

    /**
     * Determines if a given point is unshaded by any objects in the scene.
     *
     * @param gp  The geometric point being checked.
     * @param l   The vector from the light source to the point.
     * @param n   The normal vector at the point (not used in this method but may be relevant in other contexts).
     * @return    True if the point is unshaded, false if it is shaded.
     */
    private boolean unshaded(GeoPoint gp,LightSource light,Vector l , Vector n,double nl) {

        // Calculate the direction from the point to the light source
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? EPS : -EPS);
        Point point = gp.point.add(epsVector);
        // Create a ray from the geometric point in the direction of the light source
        Ray ray = new Ray(point, lightDirection);
        // Find intersections of the ray with objects in the scene
        List<Point> intersections = scene.geometries.findIntersections(ray);
        // If there are no intersections, the point is unshaded
        if(intersections==null)return true;
        for(Point intersection : intersections) {
            if(light.getDistance(intersection)>intersection.distance(ray.getHead()))return false;
        }
        return true;
    }

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
            if ((nl * nv > 0)&&unshaded(gp,lightSource,l,n,nl)) {  // sign(nl) == sign(nv)

                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(calcDiffusive(material, nl, iL)).add(
                        calcSpecular(material, n, material.shininess, nl, v, l, iL));
            }
//
        }

        return color;
    }
}
