package renderer;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    /**
     * Radius of the sphere
     */
    private static final double SPHERE_RADIUS = 50d;
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(150, 150).setVpDistance(1000);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(200, 200).setVpDistance(1000);
    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKD(KD3).setKS(KS3).setShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);
    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };
    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    /**
     * Position of the light in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);
    /**
     * Light direction (directional and spot) in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);

    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere(sphereCenter, SPHERE_RADIUS)
            .setEmission(sphereColor).setMaterial(new Material().setKD(KD).setKS(KS).setShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKL(0.001).setKQ(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build() //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKL(0.001).setKQ(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spotlight
     */

    @Test
    public void sphereSpotSharp() throws CloneNotSupportedException {
        scene1.geometries.add(sphere);

        SpotLight spotLight = (SpotLight) new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                .setKL(0.001)
                .setKQ(0.00004)
                .setNarrowBeam(10);

        scene1.lights.add(spotLight); // הוספת מקור האור לרשימת מקורות האור בסצנה

        camera1.setImageWriter(new ImageWriter("lightSphereSpotSharp", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }



    /**
     * Produce a picture of two triangles lighted by a narrow spotlight
     */
    @Test
    public void trianglesSpotSharp() throws CloneNotSupportedException {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKL(0.001).setKQ(0.00004).setNarrowBeam(10));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotSharp", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }



    /**
     * Produce a picture of two triangles lighted by all light types
     */

    @Test
    public void sphereAllLights() throws CloneNotSupportedException {
        // הגדרת הסצנה והוספת הספירה
        scene1.geometries.add(new Sphere(new Point(0, 0, -50), SPHERE_RADIUS)
                .setEmission(new Color(BLUE).reduce(2))
                .setMaterial(new Material().setKD(KD).setKS(KS).setShininess(SHININESS)));

        // הוספת מקורות האור
        scene1.lights.add(new SpotLight(new Color(255, 165, 0), new Point(-50, -50, 25), new Vector(1, 1, -0.5))
                .setKL(0.001).setKQ(0.0001).setNarrowBeam(10));
        scene1.lights.add(new PointLight(new Color(255, 69, 0), new Point(0, 0, 500))
                .setKL(0.0001).setKQ(0.00001));
        scene1.lights.add(new DirectionLight(new Color(255, 69, 0), new Vector(-1, -1, -0.5)));

        camera1.setImageWriter(new ImageWriter("sphereAllLights", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Test method to produce an image of two triangles illuminated by multiple light sources.
     *
     * @throws CloneNotSupportedException if cloning the geometry is not supported
     */
    @Test
    public void trianglesAllLights() throws CloneNotSupportedException {
        // Create two red triangles with specified vertices, emission color, and material
        Geometry redTriangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
                .setEmission(new Color(RED))
                .setMaterial(material);
        Geometry redTriangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
                .setEmission(new Color(RED))
                .setMaterial(material);

        // Add the triangles to the scene's geometries
        scene2.geometries.add(redTriangle1, redTriangle2);

        // Add a directional light with green color and specified direction
        scene2.lights.add(new DirectionLight(new Color(GREEN), new Vector(1, -1, -1)));

        // Add a point light with blue color, specified position, and attenuation factors
        scene2.lights.add(new PointLight(new Color(BLUE), new Point(-20, 40, -80))
                .setKL(0.001).setKQ(0.0002));

        // Add a spot light with yellow color, specified position, direction, and narrow beam angle
        scene2.lights.add(new SpotLight(new Color(YELLOW), new Point(20, 30, -90), new Vector(-1, -1, -1))
                .setKL(0.001).setKQ(0.0001).setNarrowBeam(10));

        // Set the image writer for the camera, build the camera, render the image, and write the image to file
        camera2.setImageWriter(new ImageWriter("lightTrianglesAllLights", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }



//    @Test
//    public void sphereSpotSharpBonus() throws CloneNotSupportedException {
//        // הגדרת הסצנה והוספת הספירה
//        scene1.geometries.add(new Sphere(new Point(0, 0, -50), SPHERE_RADIUS)
//                .setEmission(new Color(BLUE).reduce(2))
//                .setMaterial(new Material().setKD(KD).setKS(KS).setShininess(SHININESS)));
//
//        // הוספת מקור האור
//        SpotLight spotLight = (SpotLight) new SpotLight(new Color(255, 165, 0), new Point(-50, -50, 25), new Vector(1, 1, -0.5))
//                .setKL(0.001)
//                .setKQ(0.00004)
//                .setNarrowBeam(10);
//
//        scene1.lights.add(spotLight); // הוספת מקור האור לרשימת מקורות האור בסצנה
//
//        camera1.setImageWriter(new ImageWriter("sphereSpotSharpBonus", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//    @Test
//    public void trianglesSpotSharpBonus() throws CloneNotSupportedException {
//        // הגדרת הסצנה והוספת המשולשים
//        Geometry redTriangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
//                .setEmission(new Color(RED))
//                .setMaterial(material);
//        Geometry redTriangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
//                .setEmission(new Color(RED))
//                .setMaterial(material);
//
//        scene2.geometries.add(redTriangle1, redTriangle2);
//
//        // הוספת מקור האור
//        scene2.lights.add(new SpotLight(new Color(GREEN), trianglesLightPosition, trianglesLightDirection)
//                .setKL(0.001)
//                .setKQ(0.00004)
//                .setNarrowBeam(10));
//
//        camera2.setImageWriter(new ImageWriter("trianglesSpotSharpBonus", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//    }
//


}