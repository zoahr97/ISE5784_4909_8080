package renderer;

import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.*;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * The SceneLoader class is responsible for loading a scene from an XML file.
 */
public class SceneLoader {

    /**
     * Loads a scene from an XML file.
     *
     * @param filePath the path to the XML file
     * @return the loaded Scene object, or null if an error occurs
     */
    public Scene loadSceneFromFile(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Read scene name
            Element sceneElement = (Element) doc.getElementsByTagName("scene").item(0);
            String sceneName = sceneElement.getAttribute("name");

            Scene scene = new Scene(sceneName);

            // Read background color
            String backgroundColorStr = sceneElement.getAttribute("background-color");
            Color backgroundColor = parseColor(backgroundColorStr);
            scene.setBackground(backgroundColor);

            // Read ambient light
            NodeList ambientLightNodeList = doc.getElementsByTagName("ambientLight");
            if (ambientLightNodeList.getLength() > 0) {
                Element ambientLightElement = (Element) ambientLightNodeList.item(0);
                String ambientColorStr = ambientLightElement.getAttribute("color");
                Color ambientColor = parseColor(ambientColorStr);
                double k = Double.parseDouble(ambientLightElement.getAttribute("k"));
                scene.setAmbientLight(new AmbientLight(ambientColor, new Double3(k)));
            } else {
                System.out.println("Ambient light element not found.");
            }

            // Read geometries
            NodeList geometryNodes = doc.getElementsByTagName("geometries").item(0).getChildNodes();
            Geometries geometries = new Geometries();

            for (int i = 0; i < geometryNodes.getLength(); i++) {
                Node node = geometryNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    switch (element.getTagName()) {
                        case "sphere":
                            Point center = parsePoint(element.getAttribute("center"));
                            double radius = Double.parseDouble(element.getAttribute("radius"));
                            geometries.add(new Sphere(center, radius));
                            break;
                        case "triangle":
                            Point p0 = parsePoint(element.getAttribute("p0"));
                            Point p1 = parsePoint(element.getAttribute("p1"));
                            Point p2 = parsePoint(element.getAttribute("p2"));
                            geometries.add(new Triangle(p0, p1, p2));
                            break;
                        default:
                            System.out.println("Unknown geometry type: " + element.getTagName());
                            break;
                    }
                }
            }

            scene.setGeometries(geometries);
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a color from a string.
     *
     * @param colorStr the string representing the color in the format "r g b"
     * @return the parsed Color object
     */
    private Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    /**
     * Parses a point from a string.
     *
     * @param pointStr the string representing the point in the format "x y z"
     * @return the parsed Point object
     */
    private Point parsePoint(String pointStr) {
        String[] coords = pointStr.split(" ");
        return new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
    }
}
