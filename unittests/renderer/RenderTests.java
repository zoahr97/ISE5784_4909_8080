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


}

