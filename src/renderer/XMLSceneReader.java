package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLSceneReader {

    /**
     * Reads a scene from an XML file and returns a Scene object.
     *
     * @param filePath the path to the XML file
     * @return a Scene object constructed from the XML data
     */
    public static Scene readSceneFromXML(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Getting scene attributes
            String backgroundColor = doc.getDocumentElement().getAttribute("background-color");
            String ambientLightColor = doc.getElementsByTagName("ambient-light").item(0).getAttributes().getNamedItem("color").getNodeValue();
            double ambientLightKValue = Double.parseDouble(doc.getElementsByTagName("ambient-light").item(0).getAttributes().getNamedItem("k").getNodeValue());
            Double3 ambientLightK = new Double3(ambientLightKValue);

            // Creating scene
            Scene scene = new Scene("Scene from XML");
            scene.setBackground(parseColor(backgroundColor));
            scene.setAmbientLight(new AmbientLight(parseColor(ambientLightColor), ambientLightK));

            // Getting geometries
            NodeList geometryList = doc.getElementsByTagName("geometries").item(0).getChildNodes();
            for (int temp = 0; temp < geometryList.getLength(); temp++) {
                Node node = geometryList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    switch (element.getTagName()) {
                        case "sphere":
                            parseAndAddSphere(element, scene);
                            break;
                        case "triangle":
                            parseAndAddTriangle(element, scene);
                            break;
                        // Add cases for other geometries if needed
                    }
                }
            }

            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a sphere element and adds it to the scene.
     *
     * @param element the sphere element
     * @param scene   the scene to add the sphere to
     */
    private static void parseAndAddSphere(Element element, Scene scene) {
        double radius = Double.parseDouble(element.getAttribute("radius"));
        String[] centerCoords = element.getAttribute("center").split(" ");
        Point center = new Point(Double.parseDouble(centerCoords[0]),
                Double.parseDouble(centerCoords[1]),
                Double.parseDouble(centerCoords[2]));
        Sphere sphere = new Sphere(center, radius);
        scene.geometries.add(sphere);
    }

    /**
     * Parses a triangle element and adds it to the scene.
     *
     * @param element the triangle element
     * @param scene   the scene to add the triangle to
     */
    private static void parseAndAddTriangle(Element element, Scene scene) {
        String[] p0Coords = element.getAttribute("p0").split(" ");
        Point p0 = new Point(Double.parseDouble(p0Coords[0]),
                Double.parseDouble(p0Coords[1]),
                Double.parseDouble(p0Coords[2]));

        String[] p1Coords = element.getAttribute("p1").split(" ");
        Point p1 = new Point(Double.parseDouble(p1Coords[0]),
                Double.parseDouble(p1Coords[1]),
                Double.parseDouble(p1Coords[2]));

        String[] p2Coords = element.getAttribute("p2").split(" ");
        Point p2 = new Point(Double.parseDouble(p2Coords[0]),
                Double.parseDouble(p2Coords[1]),
                Double.parseDouble(p2Coords[2]));

        Triangle triangle = new Triangle(p0, p1, p2);
        scene.geometries.add(triangle);
    }

    /**
     * Parses a color string in the format "R G B" and returns a Color object.
     *
     * @param colorString the color string
     * @return a Color object
     */
    private static Color parseColor(String colorString) {
        String[] components = colorString.split(" ");
        int r = Integer.parseInt(components[0]);
        int g = Integer.parseInt(components[1]);
        int b = Integer.parseInt(components[2]);
        return new Color(r, g, b);
    }
}