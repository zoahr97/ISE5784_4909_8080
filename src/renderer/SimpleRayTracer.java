package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import primitives.Vector;
import scene.Scene;


import java.util.*;

import static java.awt.AWTEventMulticaster.add;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SimpleRayTracer is a class that extends RayTracerBase and provides basic
 * ray tracing functionality for rendering a scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Maximum recursion depth for calculating color effects in ray tracing.
     * Controls the maximum number of global lighting reflections/refractions.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum attenuation coefficient `k` value for terminating recursion in ray tracing.
     * Controls the minimum contribution of global lighting effects.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * A small constant used to offset rays to avoid numerical issues such as self-intersection.
     * This value is crucial in ray tracing algorithms to prevent a ray from incorrectly
     * intersecting the surface it originates from due to floating-point precision errors.
     */
    private static final double EPS = 0.1;
    /**
     *
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Determines if a given point is unshaded by any objects in the scene.
     *
     * @param gp The geometric point being checked.
     * @param l  The vector from the light source to the point.
     * @param n  The normal vector at the point (not used in this method but may be relevant in other contexts).
     * @return True if the point is unshaded, false if it is shaded.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {

        // Calculate the direction from the point to the light source
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? EPS : -EPS);
        Point point = gp.point.add(epsVector);
        // Create a ray from the geometric point in the direction of the light source
        Ray ray = new Ray(point, lightDirection, n);
        // Find intersections of the ray with objects in the scene
        List<Point> intersections = scene.geometries.findIntersections(ray);
        // If there are no intersections, the point is unshaded
        if (intersections == null) return true;
        for (Point intersection : intersections) {
            if (light.getDistance(intersection) > intersection.distance(ray.getHead())) return false;
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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background :
                calcColor(closestPoint, ray);
    }
    public Color traceRayWithAdaptiveSupersampling(Point centerP, double width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        return AdaptiveSuperSamplingRec(centerP,width,Height,minWidth,minHeight,cameraLoc,Vright,Vup,prePoints);
    }

    /**
     * Traces a list of rays and calculates the average color they hit.
     *
     * @param rays the list of rays to trace
     * @return the average color of all the rays
     */
    public Color traceRays(List<Ray> rays) {
        if (rays == null || rays.isEmpty()) {
            throw new IllegalArgumentException("Rays list cannot be null or empty");
        }
        Color finalColor = Color.BLACK;

        for (Ray ray : rays) {
            Color color = traceRay(ray); // Trace each ray and get the color
            finalColor = finalColor.add(color); // Add the color to the final color
        }

        int numRays = rays.size();
        if (numRays <= 0) {
            throw new IllegalStateException("Number of rays must be greater than 0");
        }

        return finalColor.reduce(numRays); // Return the average color

    }


    /**
     * Calculates the global effect (reflection/refraction) of a ray.
     *
     * @param ray   the ray to trace
     * @param level the recursion level for reflection/refraction
     * @param k     the attenuation factor
     * @param kx    the specific attenuation for the current effect (reflection/refraction)
     * @return the color resulting from the global effect
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;

        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);

        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);

    }


    /**
     * Calculates the combined global effects (reflection and refraction) at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that intersects the point
     * @param level the recursion level for reflection/refraction
     * @param k     the attenuation factor
     * @return the combined color resulting from reflection and refraction
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,
                                    int level, Double3 k) {

        Color color = Color.BLACK;
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n),
                level, k, material.kR).
                add(calcGlobalEffect(constructRefractedRay(gp, v, n),
                        level, k, material.kT));
    }


    /**
     * Calculates the color at a given intersection point `gp` on a surface for a ray `ray`.
     * Includes both local and global lighting effects recursively up to `level` depth.
     *
     * @param gp    The intersection point on the surface.
     * @param ray   The ray being traced.
     * @param level The current recursion level for global effects.
     * @param k     The attenuation factors for ambient, diffuse, and specular light.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the color at a given intersection point `gp` on a surface for a ray `ray`.
     * Uses maximum calculation depth for global effects and initial attenuation factors.
     * Adds ambient light intensity from the scene.
     *
     * @param gp  The intersection point on the surface.
     * @param ray The ray being traced.
     * @return The final calculated color at the intersection point including ambient light.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Constructs a reflected ray based on the incoming ray, the normal at the intersection point, and the geometry.
     *
     * @param gp the intersection point
     * @param v  the direction vector of the incoming ray
     * @param n  the normal vector at the intersection point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        // r = v - 2 * (v . n) * n
        double vn = v.dotProduct(n);
        if (isZero(vn)) {
            return null; // If the dot product is zero, the reflection doesn't happen
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a refracted ray based on the incoming ray, the normal at the intersection point, and the geometry.
     *
     * @param gp the intersection point
     * @param v  the direction vector of the incoming ray
     * @param n  the normal vector at the intersection point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n); // Refracted ray simply continues in the same direction
    }

    /**
     * Finds the closest intersection point between the given ray and any object in the geometries collection.
     * Returns the intersection point as a GeoPoint object.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest intersection point with any object in the geometries collection, or null if no intersection found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
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
     * Calculates the local effects (diffusive and specular) of lighting at a given geometry point.
     *
     * @param gp  The geometry point where the effects are being calculated.
     * @param ray The ray that intersects the geometry point.
     * @param k   The transparency coefficient.
     * @return The color resulting from the local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        if (gp == null || gp.geometry == null) {
            return Color.BLACK; // Return black color or handle the error as needed
        }
        Vector n = gp.geometry.getNormal(gp.point); // Normal vector at the geometry point
        Vector v = ray.getDirection().normalize();  // Direction vector of the ray, normalized
        double nv = alignZero(n.dotProduct(v));     // Dot product of the normal vector and direction vector

        // If the dot product is zero, return black color as there is no effect
        if (nv == 0) return Color.BLACK;

        Material material = gp.geometry.getMaterial(); // Material of the geometry at the point
        Color color = gp.geometry.getEmission();       // Emission color of the geometry

        // Loop over all light sources in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point).normalize(); // Vector from the point to the light source, normalized
            double nl = alignZero(n.dotProduct(l));            // Dot product of the normal vector and the light vector

            // If the signs of the dot products are the same, calculate the lighting effects
            if ((nl * nv > 0)) {  // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp, lightSource, l, n, nv); // Transparency coefficient
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    // Scale the light intensity by the transparency coefficient
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);

                    // Add the diffusive and specular components to the color
                    color = color.add(calcDiffusive(material, nl, iL))
                            .add(calcSpecular(material, n, material.shininess, nl, v, l, iL));
                }
            }
        }

        return color; // Return the resulting color
    }


    /**
     * Calculates the transparency factor between a geometry point and a light source.
     *
     * @param gp    The geometry point.
     * @param light The light source.
     * @param l     The vector from the point to the light source.
     * @param n     The normal vector at the geometry point.
     * @param nv    The dot product of the normal vector and the vector to the light source.
     * @return The transparency factor as a Double3 object.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {
        // Find all intersections between the ray and the scene geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(new Ray(gp.point, l.scale(-1), n));

        // If there are no intersections, the ray is unobstructed and not shaded
        if (intersections == null) return Double3.ONE;

        Double3 ktr = Double3.ONE;

        // Loop over all intersection points
        for (GeoPoint intersection : intersections) {
            if (intersection.point.distance(gp.point) < light.getDistance(gp.point)) {
                // Multiply ktr by the transparency factor of the geometry at the intersection point
                ktr = ktr.product(intersection.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO; // The ray is completely obstructed
                }
            }
        }

        return ktr;
    }

    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            Vector direction = centerP.subtract(cameraLoc);
//            if (direction.equals(Vector.ZERO)) {
//                return primitives.Color.BLACK;
//            }
            return this.traceRay(new Ray(cameraLoc, direction));
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;

        // Iterate over the corners of the pixel and perform sub-sampling
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                // Check if the sub-pixel's corner is already sampled
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    Vector direction = tempCorner.subtract(cameraLoc);
                    if (!direction.equals(Vector.ZERO)) {
                        tempRay = new Ray(cameraLoc, direction);
                        nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                        colorList.add(traceRay(tempRay));
                    }
                }
            }
        }

        if (nextCenterPList.isEmpty()) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        // Check if all colors in the colorList are almost equal
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            // If all colors are equal and there is more than one color, return the first color.
            return tempColor;

        tempColor = primitives.Color.BLACK;
        // Recursively perform adaptive super-sampling on sub-pixels
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width / 2, Height / 2, minWidth, minHeight, cameraLoc, Vright, Vup, cornersList));
        }
        // Reduce the color by dividing by the number of sub-pixels
        return tempColor.reduce(nextCenterPList.size());
    }

    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if (point.equals(tempPoint))
                return true;
        }
        return false;
    }
}