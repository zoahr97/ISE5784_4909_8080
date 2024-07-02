package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.YELLOW;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

//    @Test
//    public void renderXmlScene() throws CloneNotSupportedException {
//        // Read the scene from XML file
//        Scene scene2 = XMLSceneReader.readSceneFromXML("C:\\Users\\User\\IdeaProjects\\ISE5784_4909_8080\\resources\\renderTestTwoColors.xml");
//
//        // Perform assertions based on XML data
//        assertNotNull(scene2);
//        assertEquals(scene2.name, "Scene from XML", "Scene name is incorrect");
//
//        Color expectedBackgroundColor = new Color(75, 127, 90); // Green color
//        assertEquals(expectedBackgroundColor.getColor(), scene2.background.getColor(),
//                "Background color is incorrect");
//
//        AmbientLight expectedAmbientLight = new AmbientLight(new Color(255, 191, 191), Double3.ONE);
//        assertEquals(expectedAmbientLight.getIntensity().getColor(), scene2.ambientLight.getIntensity().getColor(),
//                "Ambient light is incorrect");
//
//        // Set up the camera and render the image
//        Camera camera2 = Camera.getBuilder()
//                .setRayTracer(new SimpleRayTracer(scene2))
//                .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVpDistance(100)
//                .setVpSize(500, 500)
//                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//                .build();
//
//        camera2.renderImage()
//                .printGrid(100, new Color(YELLOW))
//                .writeToImage();
//    }
}