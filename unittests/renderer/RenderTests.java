package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;


import javax.swing.*;
import java.util.List;

import static java.awt.Color.YELLOW;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    /**
     * Scene of the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void renderTwoColorTest() throws CloneNotSupportedException {
        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(75, 127, 90));

        // right
        camera
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(255, 255, 0))
                .writeToImage();
    }

    /**
     * Unit test for XMLSceneReader class.
     * * @author Dvora and zohar
     */


    @Test
    public void renderXmlSceneTest() throws CloneNotSupportedException {
        SceneLoader sceneLoader = new SceneLoader();
        Scene scene = sceneLoader.loadSceneFromFile("C:\\Users\\enava\\ISE5784_4909_8080\\resources\\renderTestTwoColors.xml");

        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(100)
                .setVpSize(500, 500);

        camera
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .build()
                .renderImage()
                .printGrid(100, new Color(255, 255, 0))
                .writeToImage();
    }

//    @Test
//    public void renderFinal() throws CloneNotSupportedException {
//        Scene scene1 = new Scene("Test NewScene")
//                .setBackground(new Color(0, 0, 0))
//                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
//                .setGeometries(new Geometries())
//                .setLights(List.of(
//                        new SpotLight(new Color(400, 300, 300), new Point(50, 50, 50), new Vector(-1, -1, -2))
//                                .setKL(0.00001).setKQ(0.000001),
//                        new PointLight(new Color(500, 300, 300), new Point(-50, -50, -50))
//                                .setKL(0.00001).setKQ(0.000001)
//                ));
//
//        // Add geometries to the scene
//        scene1.geometries.add(
//                new Sphere(new Point(0, 0, -100), 50)
//                        .setEmission(new Color(java.awt.Color.BLUE))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
//                new Triangle(new Point(-100, 0, -150), new Point(0, 100, -150), new Point(100, 0, -150))
//                        .setEmission(new Color(java.awt.Color.GREEN))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
//                new Plane(new Point(0, 0, -150), new Vector(0, 1, 0))
//                        .setEmission(new Color(java.awt.Color.RED))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
//                new Polygon(new Point(-50, -50, -200), new Point(50, -50, -200), new Point(50, 50, -200), new Point(-50, 50, -200))
//                        .setEmission(new Color(java.awt.Color.YELLOW))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5)))
//        );
//        Camera camera = Camera.getBuilder()
//                .setRayTracer(new SimpleRayTracer(scene1))
//                .setLocation(Point.ZERO)
//                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVpDistance(100)
//                .setVpSize(500, 500)
//                .setImageWriter(new ImageWriter("simple render test", 1000, 1000))
//                .build();
//
//        camera.renderImage()
//                .writeToImage();
//
//
//
//    }
//}
@Test
public void renderComplexScene() throws CloneNotSupportedException {
    // Create the scene
    Scene scene1 = new Scene("Complex Scene")
            .setBackground(new Color(20, 20, 20))
            .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
            .setGeometries(new Geometries())
            .setLights(List.of(
                    new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                            .setKL(0.00001).setKQ(0.000001),
                    new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                            .setKL(0.00001).setKQ(0.000001),
                    new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
            ));

    // Add geometries to the scene
    scene1.geometries.add(
            new Sphere(new Point(0, 0, -400), 200)
                    .setEmission(new Color(java.awt.Color.BLUE))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
            new Sphere(new Point(-200, -200, -600), 120)
                    .setEmission(new Color(java.awt.Color.RED))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
            new Sphere(new Point(200, 200, -600), 120)
                    .setEmission(new Color(java.awt.Color.GREEN))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
            new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                    .setEmission(new Color(java.awt.Color.GREEN))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
            new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                    .setEmission(new Color(java.awt.Color.RED))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
            new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                    .setEmission(new Color(java.awt.Color.YELLOW))
                    .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5)))

    );

    // Create the camera using the Builder pattern
    Camera camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 2000))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(2000)
            .setVpSize(1000, 1000)
            .setImageWriter(new ImageWriter("complex render test", 1000, 1000))
            .build();

    // Render the image and write to file
    camera.renderImage()
            .writeToImage();
}


    @Test
    public void renderTreeScene() throws CloneNotSupportedException {
        // Create the scene
        Scene scene = new Scene("Tree Scene")
                .setBackground(new Color(135, 206, 235)) // Light blue sky background
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(500, 500, 500), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-500, -500, -500))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        // Tree trunk


        // Tree branches
        scene.geometries.add(
                new Polygon(new Point(-50, 200, -150), new Point(50, 200, -150), new Point(0, 250, -300))
                        .setEmission(new Color(0, 128, 0)) // Green color for the branches
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(-50, 250, -150), new Point(50, 250, -150), new Point(0, 300, -300))
                        .setEmission(new Color(0, 128, 0))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Additional branches
        scene.geometries.add(
                new Polygon(new Point(-100, 300, -100), new Point(100, 300, -100), new Point(0, 350, -250))
                        .setEmission(new Color(0, 128, 0))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(-100, 350, -100), new Point(100, 350, -100), new Point(0, 400, -250))
                        .setEmission(new Color(0, 128, 0))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Ground
        scene.geometries.add(
                new Plane(new Point(0, -1, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(34, 139, 34)) // Green color for the ground
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Flowers (Spheres as flowers)
        scene.geometries.add(
                new Sphere(new Point(-150, 0, -200), 20)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-130, 0, -250), 20)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-110, 0, -300), 20)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(150, 0, -200), 20)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(130, 0, -250), 20)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(110, 0, -300), 20)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Create the camera
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 300, 1500)) // Adjusted the camera location for a better view
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) // Orthogonal vectors
                .setVpDistance(1500)
                .setVpSize(2000, 2000)
                .setImageWriter(new ImageWriter("tree_scene_render", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }

    @Test
    public void renderLargeHouseScene() throws CloneNotSupportedException {
        // Create the scene
        Scene scene = new Scene("Large House Scene")
                .setBackground(new Color(135, 206, 235)) // Light blue sky background
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(500, 500, 500), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-500, -500, -500))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        scene.geometries.add(
                // House base
                new Polygon(new Point(-300, 0, -300), new Point(300, 0, -300), new Point(300, 0, -900), new Point(-300, 0, -900))
                        .setEmission(new Color(139, 69, 19)) // Brown color for the house base
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // House walls
                new Polygon(new Point(-300, 0, -300), new Point(-300, 300, -300), new Point(-300, 300, -900), new Point(-300, 0, -900))
                        .setEmission(new Color(210, 180, 140)) // Tan color for the walls
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(300, 0, -300), new Point(300, 300, -300), new Point(300, 300, -900), new Point(300, 0, -900))
                        .setEmission(new Color(210, 180, 140))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(-300, 300, -300), new Point(300, 300, -300), new Point(300, 300, -900), new Point(-300, 300, -900))
                        .setEmission(new Color(210, 180, 140))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // House roof
                new Triangle(new Point(-350, 300, -350), new Point(350, 300, -350), new Point(0, 500, -600))
                        .setEmission(new Color(165, 42, 42)) // Brownish-red color for the roof
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Triangle(new Point(-350, 300, -850), new Point(350, 300, -850), new Point(0, 500, -600))
                        .setEmission(new Color(165, 42, 42))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Door
                new Polygon(new Point(-50, 0, -300), new Point(50, 0, -300), new Point(50, 150, -300), new Point(-50, 150, -300))
                        .setEmission(new Color(139, 69, 19)) // Brown color for the door
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Grass
                new Plane(new Point(0, -1, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(34, 139, 34)) // Green color for the grass
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Flowers (Spheres as flowers)
                new Sphere(new Point(-200, 0, -400), 20)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-180, 0, -450), 20)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-160, 0, -500), 20)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(200, 0, -400), 20)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(180, 0, -450), 20)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(160, 0, -500), 20)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Trees (Cones as trees)
                new Triangle(new Point(-400, 0, -700), new Point(-300, 0, -700), new Point(-350, 200, -750))
                        .setEmission(new Color(34, 139, 34)) // Green color for the tree
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Triangle(new Point(400, 0, -700), new Point(300, 0, -700), new Point(350, 200, -750))
                        .setEmission(new Color(34, 139, 34))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 300, 1500)) // Adjusted the camera location for a better view
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) // Orthogonal vectors
                .setVpDistance(1500)
                .setVpSize(2000, 2000)
                .setImageWriter(new ImageWriter("large_house_scene_render", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }


    @Test
    public void renderHouseScene() throws CloneNotSupportedException {
        // Create the scene
        Scene scene = new Scene("House Scene")
                .setBackground(new Color(135, 206, 235)) // Light blue sky background
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        scene.geometries.add(
                // House base
                new Polygon(new Point(-100, 0, -100), new Point(100, 0, -100), new Point(100, 0, -300), new Point(-100, 0, -300))
                        .setEmission(new Color(139, 69, 19)) // Brown color for the house base
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // House walls
                new Polygon(new Point(-100, 0, -100), new Point(-100, 100, -100), new Point(-100, 100, -300), new Point(-100, 0, -300))
                        .setEmission(new Color(210, 180, 140)) // Tan color for the walls
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(100, 0, -100), new Point(100, 100, -100), new Point(100, 100, -300), new Point(100, 0, -300))
                        .setEmission(new Color(210, 180, 140))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Polygon(new Point(-100, 100, -100), new Point(100, 100, -100), new Point(100, 100, -300), new Point(-100, 100, -300))
                        .setEmission(new Color(210, 180, 140))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // House roof
                new Triangle(new Point(-120, 100, -120), new Point(120, 100, -120), new Point(0, 180, -200))
                        .setEmission(new Color(165, 42, 42)) // Brownish-red color for the roof
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Triangle(new Point(-120, 100, -280), new Point(120, 100, -280), new Point(0, 180, -200))
                        .setEmission(new Color(165, 42, 42))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Door
                new Polygon(new Point(-30, 0, -100), new Point(30, 0, -100), new Point(30, 60, -100), new Point(-30, 60, -100))
                        .setEmission(new Color(139, 69, 19)) // Brown color for the door
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Grass
                new Plane(new Point(0, -1, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(34, 139, 34)) // Green color for the grass
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                // Flowers (Spheres as flowers)
                new Sphere(new Point(-70, 0, -150), 10)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-60, 0, -160), 10)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(-50, 0, -170), 10)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(70, 0, -150), 10)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(60, 0, -160), 10)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)),
                new Sphere(new Point(50, 0, -170), 10)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 100, 500)) // Adjusted the camera location for a better view
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)) // Orthogonal vectors
                .setVpDistance(500)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("house_scene_render", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }
    @Test
    public void renderComplexScen() throws CloneNotSupportedException {
        // Create the scene
        Scene scene1 = new Scene("Complex Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        scene1.geometries.add(
                new Sphere(new Point(0, 0, -400), 200)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(-200, -200, -600), 120)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(200, 200, -600), 120)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3)))
        );

        // Add tiles to the background
        int tileSize = 100; // Size of each tile
        int rows = 10;      // Number of rows
        int cols = 10;      // Number of columns
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = i * tileSize - (rows * tileSize / 2);
                int y = j * tileSize - (cols * tileSize / 2);
                scene1.geometries.add(
                        new Polygon(
                                new Point(x, y, -1000),
                                new Point(x + tileSize, y, -1000),
                                new Point(x + tileSize, y + tileSize, -1000),
                                new Point(x, y + tileSize, -1000)
                        )
                                .setEmission(new Color(100 + (i * 20) % 155, 100 + (j * 20) % 155, 100 + ((i + j) * 10) % 155))
                                .setMaterial(new Material().setKD(0.4).setKS(0.6).setShininess(50))
                );
            }
        }

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene1))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("_test", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }


    @Test
    public void renderComplexScene4() throws CloneNotSupportedException {
        // Create the scene
        Scene scene1 = new Scene("Complex Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        scene1.geometries.add(
                new Sphere(new Point(0, 0, -400), 200)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(-200, -200, -600), 120)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(200, 200, -600), 120)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene1))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("complex_render_test", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }
    @Test
    public void renderComplexScene5() throws CloneNotSupportedException {
        // Create the scene
        Scene scene1 = new Scene("Complex Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add original geometries to the scene
        scene1.geometries.add(
                new Sphere(new Point(0, 0, -400), 200)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(-200, -200, -600), 120)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(200, 200, -600), 120)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3)))
        );

        // Add additional geometries to the sides
        scene1.geometries.add(
                // Spheres on the sides
                new Sphere(new Point(1000, 0, -600), 100)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(-1000, 0, -600), 100)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, 1000, -600), 100)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, -1000, -600), 100)
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),

                // Triangles on the sides
                new Triangle(new Point(800, -800, -700), new Point(1000, -800, -700), new Point(900, -600, -700))
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(-800, -800, -700), new Point(-1000, -800, -700), new Point(-900, -600, -700))
                        .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(800, 800, -700), new Point(1000, 800, -700), new Point(900, 600, -700))
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(-800, 800, -700), new Point(-1000, 800, -700), new Point(-900, 600, -700))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),

                // Polygons on the sides
                new Polygon(new Point(500, -500, -800), new Point(700, -500, -800), new Point(700, -300, -800), new Point(500, -300, -800))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(-500, -500, -800), new Point(-700, -500, -800), new Point(-700, -300, -800), new Point(-500, -300, -800))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(500, 500, -800), new Point(700, 500, -800), new Point(700, 300, -800), new Point(500, 300, -800))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(-500, 500, -800), new Point(-700, 500, -800), new Point(-700, 300, -800), new Point(-500, 300, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene1))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("nice", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }

    @Test
    public void renderComplexScene1() throws CloneNotSupportedException {
        // Create the scene
        Scene scene1 = new Scene("Complex Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add original geometries to the scene
        scene1.geometries.add(
                new Sphere(new Point(0, 0, -400), 200)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(-200, -200, -600), 120)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(200, 200, -600), 120)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3)))
        );

        // Add additional geometries to the sides
        scene1.geometries.add(
                // Spheres on the sides
                new Sphere(new Point(1000, 0, -600), 100)
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(-1000, 0, -600), 100)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, 1000, -600), 100)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, -1000, -600), 100)
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),

                // Triangles on the sides
                new Triangle(new Point(800, -800, -700), new Point(1000, -800, -700), new Point(900, -600, -700))
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(-800, -800, -700), new Point(-1000, -800, -700), new Point(-900, -600, -700))
                        .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(800, 800, -700), new Point(1000, 800, -700), new Point(900, 600, -700))
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(-800, 800, -700), new Point(-1000, 800, -700), new Point(-900, 600, -700))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),

                // Polygons on the sides
                new Polygon(new Point(500, -500, -800), new Point(700, -500, -800), new Point(700, -300, -800), new Point(500, -300, -800))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(-500, -500, -800), new Point(-700, -500, -800), new Point(-700, -300, -800), new Point(-500, -300, -800))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(500, 500, -800), new Point(700, 500, -800), new Point(700, 700, -800), new Point(500, 700, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(-500, 500, -800), new Point(-700, 500, -800), new Point(-700, 700, -800), new Point(-500, 700, -800))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene1))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("complex_2", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }

    @Test
    public void render() throws CloneNotSupportedException {
        // Create the scene
        Scene scene1 = new Scene("C")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add geometries to the scene
        scene1.geometries.add(
                // Original geometries
                new Sphere(new Point(0, 0, -400), 200)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(-200, -200, -600), 120)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(200, 200, -600), 120)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(java.awt.Color.WHITE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3))),

                // Additional shapes to fill the scene
                new Sphere(new Point(800, -400, -500), 80)
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(-800, -400, -500), 80)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, -800, -500), 100)
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
                new Sphere(new Point(0, 800, -500), 100)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),

                // Additional triangles and polygons
                new Triangle(new Point(-300, 500, -600), new Point(-100, 500, -600), new Point(-200, 700, -600))
                        .setEmission(new Color(java.awt.Color.PINK))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Triangle(new Point(300, 500, -600), new Point(100, 500, -600), new Point(200, 700, -600))
                        .setEmission(new Color(java.awt.Color.LIGHT_GRAY))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3))),
                new Polygon(new Point(-400, -400, -200), new Point(400, -400, -200), new Point(400, 400, -200), new Point(-400, 400, -200))
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene1))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("case", 1000, 1000))
                .build();
        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }


        // Render the image and write to file


//        @Test
//
//    public void renderComplexScene2() throws CloneNotSupportedException {
//        // Create the scene
//        Scene scene1 = new Scene(" Scene2")
//                .setBackground(new Color(20, 20, 20))
//                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
//                .setGeometries(new Geometries())
//                .setLights(List.of(
//                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
//                                .setKL(0.00001).setKQ(0.000001),
//                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
//                                .setKL(0.00001).setKQ(0.000001),
//                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
//                ));
//
//        // Add geometries to the scene
//        scene1.geometries.add(
//                new Sphere(new Point(0, 0, -400), 200)
//                        .setEmission(new Color(java.awt.Color.BLUE))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
//                new Sphere(new Point(-200, -200, -600), 120)
//                        .setEmission(new Color(java.awt.Color.RED))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
//                new Sphere(new Point(200, 200, -600), 120)
//                        .setEmission(new Color(java.awt.Color.GREEN))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
//                new Triangle(new Point(-400, 0, -600), new Point(0, 400, -600), new Point(400, 0, -600))
//                        .setEmission(new Color(java.awt.Color.GREEN))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
//                new Plane(new Point(0, 0, -800), new Vector(0, 1, 0))
//                        .setEmission(new Color(java.awt.Color.RED))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
//                new Polygon(new Point(-200, -200, -800), new Point(200, -200, -800), new Point(200, 200, -800), new Point(-200, 200, -800))
//                        .setEmission(new Color(java.awt.Color.YELLOW))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkR(new Double3(0.5))),
//                new Sphere(new Point(0, 0, -100), 50)
//                        .setEmission(new Color(java.awt.Color.WHITE))
//                        .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(200).setkT(new Double3(0.9)).setkR(new Double3(0.5))),
//                new Triangle(new Point(-100, -100, -300), new Point(100, -100, -300), new Point(0, 100, -300))
//                        .setEmission(new Color(java.awt.Color.CYAN))
//                        .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(150).setkT(new Double3(0.6)).setkR(new Double3(0.3))),
//
//                // Additional spheres to reduce black background and add more shapes
//                new Sphere(new Point(800, 200, -500), 80)
//                        .setEmission(new Color(java.awt.Color.PINK))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
//                new Sphere(new Point(-800, 200, -500), 80)
//                        .setEmission(new Color(java.awt.Color.ORANGE))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
//                new Sphere(new Point(0, -800, -500), 100)
//                        .setEmission(new Color(java.awt.Color.CYAN))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3))),
//                new Sphere(new Point(0, 800, -500), 100)
//                        .setEmission(new Color(java.awt.Color.YELLOW))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3)))
//        );
//
//        // Create the camera using the Builder pattern
//        Camera camera = Camera.getBuilder()
//                .setRayTracer(new SimpleRayTracer(scene1))
//                .setLocation(new Point(0, 0, 2000))
//                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVpDistance(2000)
//                .setVpSize(1000, 1000)
//                .setImageWriter(new ImageWriter("complex", 1000, 1000))
//                .build();
//
//        // Render the image and write to file
//        camera.renderImage()
//                .writeToImage();
//    }


    @Test
    public void renderRealisticMarblesScene() throws CloneNotSupportedException {
        // Create the scene
        Scene scene = new Scene("Realistic Marbles Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(700, 400, 400), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add marbles (spheres) to the scene
        scene.geometries.add(
                new Sphere(new Point(0, 0, -400), 100)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.7))),
                new Sphere(new Point(-150, 100, -350), 80)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.6))),
                new Sphere(new Point(200, -100, -300), 90)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(100, 200, -450), 75)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.4))),
                new Sphere(new Point(-100, -200, -400), 85)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(150, 150, -500), 95)
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("realistic marbles render", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }

    @Test
    public void renderMarblesScene() throws CloneNotSupportedException {
        // Create the scene
        Scene scene = new Scene("Marbles Scene")
                .setBackground(new Color(20, 20, 20))
                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)))
                .setGeometries(new Geometries())
                .setLights(List.of(
                        new SpotLight(new Color(400, 300, 300), new Point(200, 200, 200), new Vector(-1, -1, -2))
                                .setKL(0.00001).setKQ(0.000001),
                        new PointLight(new Color(500, 300, 300), new Point(-200, -200, -200))
                                .setKL(0.00001).setKQ(0.000001),
                        new DirectionLight(new Color(300, 200, 200), new Vector(1, -1, -1))
                ));

        // Add marbles (spheres) to the scene
        scene.geometries.add(
                new Sphere(new Point(0, 0, -400), 100)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.7))),
                new Sphere(new Point(-150, 100, -350), 80)
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.6))),
                new Sphere(new Point(200, -100, -300), 90)
                        .setEmission(new Color(java.awt.Color.GREEN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(100, 200, -450), 75)
                        .setEmission(new Color(java.awt.Color.YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.4))),
                new Sphere(new Point(-100, -200, -400), 85)
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.5))),
                new Sphere(new Point(150, 150, -500), 95)
                        .setEmission(new Color(java.awt.Color.CYAN))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setkT(new Double3(0.3)))
        );

        // Create the camera using the Builder pattern
        Camera camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(0, 0, 2000))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(2000)
                .setVpSize(1000, 1000)
                .setImageWriter(new ImageWriter("marbles render test", 1000, 1000))
                .build();

        // Render the image and write to file
        camera.renderImage()
                .writeToImage();
    }

}

