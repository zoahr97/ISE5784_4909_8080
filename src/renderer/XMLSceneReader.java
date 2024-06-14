package renderer;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Double3;
import scene.Scene;

public class XMLSceneReader {
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
                            // Parse sphere attributes and add to scene
                            // Example: double x = Double.parseDouble(element.getAttribute("center").split(" ")[0]);
                            break;
                        case "triangle":
                            // Parse triangle attributes and add to scene
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

    private static Color parseColor(String colorString) {
        String[] components = colorString.split(" ");
        int r = Integer.parseInt(components[0]);
        int g = Integer.parseInt(components[1]);
        int b = Integer.parseInt(components[2]);
        return new Color(r, g, b);
    }
}

