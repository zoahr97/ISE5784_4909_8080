package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is responsible for writing an image with a grid pattern.
 * @author Dvora and Zohar
 */
class ImageWriterTest {

    @Test
    void testGrid() {

        ImageWriter image = new ImageWriter("grid", 800, 500);
        // Fill the entire image with yellow color
        for (int i = 0; i < image.getNx(); i++) {
            for (int j = 0; j < image.getNy(); j++) {
                image.writePixel(i, j, new Color(java.awt.Color.yellow));
            }
        }

        for (int i = 0; i <image.getNy(); i++) {
            for (int j = 0; j < image.getNx(); j++) {
                // Check if the current pixel is on a grid line
                if (i % 50 == 0 || j % 50 == 0) {
                    image.writePixel(j, i, new Color(java.awt.Color.red)); // Color the pixel on the grid line
                }
            }
        }
        image.writeToImage();

//        // Draw horizontal red lines every 50 pixels
//        for (int i = 0; i < image.getNy(); i += 50) {
//            for (int j = 0; j < image.getNx(); j++) {
//                image.writePixel(j, i, new Color(java.awt.Color.red));
//            }
//        }
//
//        // Draw vertical red lines every 50 pixels
//        for (int j = 0; j < image.getNx(); j += 50) {
//            for (int i = 0; i < image.getNy(); i++) {
//                image.writePixel(j, i, new Color(java.awt.Color.red));
//            }
//        }
//
//        // Write the image to file
//        image.writeToImage();
//    }
    }
}

