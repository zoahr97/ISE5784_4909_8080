package renderer;

import static java.awt.Color.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lighting.AmbientLight;
import org.junit.jupiter.api.Test;

import geometries.*;
//import lighting.AmbientLight;
import primitives.*;
import scene.Scene;
//import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class RenderTests {
   /** Scene of the tests */
   private final Scene  scene  = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera = Camera.getBuilder()
      .setRayTracer(new SimpleRayTracer(scene))
      .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
      .setVpDistance(100)
      .setVpSize(500, 500);

   /** Produce a scene with basic 3D model and render it into a png image with a
    * grid */
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
         .printGrid(100, new Color(255,255,0))
         .writeToImage();
   }

   /** Test for XML based scene - for bonus */
   @Test
   public void basicRenderXml() throws CloneNotSupportedException {
     // enter XML file name and parse from XML file into scene object
    // using the code you added in appropriate packages
     // ...
      // NB: unit tests is not the correct place to put XML parsing code
      Scene scene = XMLSceneReader.readSceneFromXML("renderTestTwoColors.xml");
      assertEquals("Scene name is incorrect", "Scene from XML", scene.name);

      // בדיקת תקינות צבע הרקע
      Color backgroundColor = scene.background;
      assertNotNull("Background color is null", backgroundColor);
      assertEquals(backgroundColor,new Color(75, 127, 190),"Background color is incorrect");

      // בדיקת תקינות התאורת האמביינטית
      AmbientLight ambientLight = scene.ambientLight;
      assertNotNull("Ambient light is null", ambientLight);
      assertEquals(ambientLight.getIntensity(),new Color(255, 191, 191),"Ambient light color is incorrect");
      // בדיקה נוספת של הערך K של התאורה האמביינטית
      assertEquals(ambientLight.getIntensity(),new Double3(1.0),"Ambient light K value is incorrect" );

      // בדיקת תקינות הגיאומטריות
      assertEquals( 4, scene.geometries,"Number of geometries is incorrect");
      // בדיקת תקינות כל גיאומטריה
      // לדוגמה:
      // assertEquals("Sphere center is incorrect", new Double3(0, 0, -100), scene.geometries.get(0).getCenter());
      // assertEquals("Sphere radius is incorrect", 50, scene.geometries.get(0).getRadius());

      // אם כל הבדיקות עוברות, הטסט עובר




      camera
        .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
        .build()
         .renderImage()
        .printGrid(100, new Color(YELLOW))
        .writeToImage();
   }
}







