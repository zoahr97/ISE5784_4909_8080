package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;
/*
בחבילת renderer.unittests נוסיף מודול בדיקה ImageWriterTest שיכיל בניית תמונה ראשונית – תמונה בצבע
אחד עם רשת קוים בצבע שני. בטסט הזה נבנה רשת של 16x10 ריבועים במסך )ViewPlane )רזולוציה של 800 על .500
צבעי הרקע הרשת – לבחירתכם\ן
 */
/** Testing ImageWriter*/
class ImageWriterTest {
    @Test
    void testImageWithGrid() {
        int width = 800;
        int height = 500;
        ImageWriter imageWriter = new ImageWriter("imageWithGrid", width, height);

        // Set the background color and grid color
        Color bgColor = new Color(255, 192, 203); // Pink Color
        Color gridColor = new Color(255, 255, 0); // Yellow Color

        // Calculate the dimensions of each grid square
        int squareWidth = width / 16;
        int squareHeight = height / 10;

        // Draw the grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Check if this pixel should be a grid line
                boolean isGridLine = (i % squareWidth == 0) || (j % squareHeight == 0);

                // Set the pixel color accordingly
                Color pixelColor = isGridLine ? gridColor : bgColor;

                // Write the pixel to the image
                imageWriter.writePixel(i, j, pixelColor);
            }
        }

        // Write the image to file
        imageWriter.writeToImage();
    }

}