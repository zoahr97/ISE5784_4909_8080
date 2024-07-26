package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera used for rendering scenes.
 */
public class Camera implements Cloneable {
    private Point p0; // Camera location
    private Vector vUp; // Upward direction
    private Vector vTo; // Forward direction
    private Vector vRight; // Right direction
    private Point centerPoint;
    private double height = 0;
    private double width = 0;
    private double distance = 0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    /**
     * for anti alighting
     */
    private int numRaysX, numRaysY;//number of rays for width and length







    private Camera() {
    }

    /**
     * Gets a builder for creating a camera.
     *
     * @return A new instance of the builder.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Gets the position of the camera.
     *
     * @return The camera's position.
     */
    public Point getPosition() {
        return p0;
    }

    /**
     * Gets the forward direction of the camera.
     *
     * @return The camera's forward direction.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the right direction of the camera.
     *
     * @return The camera's right direction.
     */
    public Vector getvRight() {
        return vRight.normalize();
    }

    /**
     * Gets the height of the viewport.
     *
     * @return The height of the viewport.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the upward direction of the camera.
     *
     * @return The camera's upward direction.
     */
    public Vector getvUp() {
        return vUp.normalize();
    }

    /**
     * Gets the distance from the camera to the viewport.
     *
     * @return The distance to the viewport.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the width of the viewport.
     *
     * @return The width of the viewport.
     */
    public double getWidth() {
        return width;
    }

    @Override
    public Camera clone() {
        try {
            Camera clone = (Camera) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Constructs a ray through a specified pixel in the view plane.
     *
     * @param nx the number of horizontal pixels in the view plane
     * @param ny the number of vertical pixels in the view plane
     * @param j  the horizontal index of the pixel (0-based)
     * @param i  the vertical index of the pixel (0-based)
     * @return a Ray object that starts at the camera position and goes through the specified pixel
     */
    public Ray constructRay(int nx, int ny, int j, int i) {
        // Calculate the center point of the view plane
        centerPoint = p0.add(vTo.scale(distance));

        // Calculate the height and width of a single pixel
        double Ry = alignZero(height / ny);
        double Rx = alignZero(width / nx);

        // Calculate the x and y coordinates of the pixel relative to the center of the view plane
        double xj = alignZero((j - (nx - 1) / 2.0) * Rx);
        double yi = alignZero(-(i - (ny - 1) / 2.0) * Ry);

        // Initialize the point pij to the center point of the view plane
        Point pij = centerPoint;

        // Adjust pij by the horizontal offset
        if (!isZero(xj))
            pij = pij.add(vRight.scale(xj));

        // Adjust pij by the vertical offset
        if (!isZero(yi))
            pij = pij.add(vUp.scale(yi));

        // Create the direction vector from the camera position to the pixel point and normalize it
        Vector vij = pij.subtract(p0).normalize();

        // Return a new Ray from the camera position in the direction of the pixel point
        return new Ray(p0, vij);
    }

    /**
     * Constructs a list of rays passing through a specific pixel.
     *
     * @param nx the number of pixels along the x-axis of the view plane
     * @param ny the number of pixels along the y-axis of the view plane
     * @param j  the pixel index along the x-axis
     * @param i  the pixel index along the y-axis
     * @return a list of rays passing through the specified pixel
     */

    public List<Ray> constructRays(int nx, int ny, int j, int i) {
        List<Ray> rays = new ArrayList<>();

        // Calculate the center point of the view plane
        Point centerPoint = p0.add(vTo.scale(distance));

        // Calculate the height and width of a single pixel
        double Ry = alignZero(height / ny);
        double Rx = alignZero(width / nx);

        // Calculate the x and y coordinates of the pixel relative to the center of the view plane
        double xj = alignZero((j - (nx - 1) / 2.0) * Rx);
        double yi = alignZero(-(i - (ny - 1) / 2.0) * Ry);

        // Initialize the point pij to the center point of the view plane
        Point pij = centerPoint;

        // Adjust pij by the horizontal offset
        if (!isZero(xj)) pij = pij.add(vRight.scale(xj));

        // Adjust pij by the vertical offset
        if (!isZero(yi)) pij = pij.add(vUp.scale(yi));

        // Calculate the sub-pixel dimensions
        double subPixelRx = Rx / numRaysX;
        double subPixelRy = Ry / numRaysY;

        // Generate multiple rays within the pixel
        for (int u = 0; u < numRaysX; u++) {
            for (int v = 0; v < numRaysY; v++) {
                // Calculate the jittered point within the pixel
                double xOffset = (u + Math.random()) * subPixelRx;
                double yOffset = (v + Math.random()) * subPixelRy;

                Point jitteredPij = pij;
                if (!isZero(xOffset)) jitteredPij = jitteredPij.add(vRight.scale(xOffset));
                if (!isZero(yOffset)) jitteredPij = jitteredPij.add(vUp.scale(yOffset));

                // Create the direction vector from the camera position to the jittered pixel point and normalize it
                Vector vij = jitteredPij.subtract(p0).normalize();

                // Add the new Ray to the list
                rays.add(new Ray(p0, vij));
            }
        }

        return rays;
    }


    /**
     * Renders the image by casting rays through each pixel and coloring them.
     */
    public Camera renderImage() {
        //throw new UnsupportedOperationException();
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                castRay(nx, ny, j, i);

            }
        }
        imageWriter.writeToImage();
        return this;
    }
    /**
     * Renders the image with supersampling to reduce aliasing effects.
     *
     * This method iterates over each pixel of the image and generates multiple rays for supersampling
     * to produce a more accurate color for each pixel. The resulting color for each pixel is computed
     * by averaging the colors obtained from tracing the multiple rays through the scene.
     *
     * The supersampling technique helps in improving the image quality by reducing visual artifacts
     * such as jagged edges and color distortions that can occur with lower sampling rates.
     *
     * @return the current Camera instance for method chaining.
     */


    public Camera renderImageWithSupersampling() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                castRays(nx, ny, j, i); // Use the castRays method for supersampling
            }
        }

        imageWriter.writeToImage();
        return this;
    }


    /**
     * Writes the image to the output file.
     * This method delegates the writing process to the ImageWriter instance.
     */
    public void writeToImage() {
        this.imageWriter.writeToImage();
    }

    /**
     * Casts a ray from the camera through a specific pixel on the view plane,
     * traces the ray to determine the color at the intersection point,
     * and writes the color to the image.
     *
     * @param Nx      the number of pixels in the horizontal direction of the view plane
     * @param Ny      the number of pixels in the vertical direction of the view plane
     * @param column  the column index of the pixel on the view plane
     * @param row     the row index of the pixel on the view plane
     */
    private void castRay(int Nx, int Ny, int column, int row) {
        Ray ray = constructRay(Nx, Ny, column, row);
        Color color = rayTracer.traceRay(ray); // Calculate the color of the body the ray intersects
        imageWriter.writePixel(column, row, color);
    }
    /**
     * Casts multiple rays through a specific pixel, calculates the average color,
     * and writes the color to the image.
     *
     * @param Nx the number of pixels along the x-axis of the view plane
     * @param Ny the number of pixels along the y-axis of the view plane
     * @param column the pixel index along the x-axis
     * @param row the pixel index along the y-axis
     */
    private void castRays(int Nx, int Ny, int column, int row) {
        // Get the list of rays for the pixel
        List<Ray> rays = constructRays(Nx, Ny, column, row);
// Ensure rays list is not empty
        if (rays == null || rays.isEmpty()) {
            throw new IllegalArgumentException("Rays list cannot be null or empty");
        }
        // Get the average color for the pixel by tracing the rays
        Color color = rayTracer.traceRays(rays);

        // Write the averaged color to the image
        imageWriter.writePixel(column, row, color);
    }



    /**
     * Prints a grid on the image with the specified color and interval.
     * The grid lines will be drawn every 'interval' pixels both horizontally and vertically.
     *
     * @param interval the interval between the grid lines in pixels.
     * @param color    the color of the grid lines.
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                // Check if the current pixel is on a grid line
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color); // Color the pixel on the grid line
                }
            }
        }
        imageWriter.writeToImage();
        return this;
    }


    /**
     * Builder class for constructing camera objects.
     */
    public static class Builder {
        private Camera camera;

        /**
         * Default constructor for the builder.
         */
        public Builder() {
            camera = new Camera();
        }

        /**
         * Sets the width and height of the viewport.
         *
         * @param width  The width of the viewport.
         * @param height The height of the viewport.
         * @return The current camera instance.
         * @throws IllegalArgumentException If width or height is not positive.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("width and height must be greater than 0");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the ImageWriter for the Camera.
         *
         * @param image the ImageWriter to be used by the Camera
         * @return the Builder instance to allow for method chaining
         */
        public Builder setImageWriter(ImageWriter image) {
            camera.imageWriter = image;
            return this;
        }

        /**
         * Sets the RayTracer for the Camera.
         *
         * @param ray the RayTracerBase instance to be used by the Camera
         * @return the Builder instance to allow for method chaining
         */
        public Builder setRayTracer(RayTracerBase ray) {
            camera.rayTracer = ray;
            return this;
        }
        /**
         * Sets the number of rays to be cast in the X direction for supersampling.
         *
         * @param numRaysX the number of rays to cast in the X direction.
         * @return the current Builder instance for method chaining.
         */
        public Builder setNumRaysX(int numRaysX) {
            camera.numRaysX = numRaysX;
            return this;
        }

        /**
         * Sets the number of rays to be cast in the Y direction for supersampling.
         *
         * @param numRaysY the number of rays to cast in the Y direction.
         * @return the current Builder instance for method chaining.
         */
        public Builder setNumRaysY(int numRaysY) {
            camera.numRaysY = numRaysY;
            return this;
        }


        /**
         * Sets the distance from the camera to the viewport.
         *
         * @param distance The distance to set.
         * @return The current camera instance.
         * @throws IllegalArgumentException If the distance is negative.
         */
        public Builder setVpDistance(int distance) {
            if (distance < 0) {
                throw new IllegalArgumentException("distance has to be positive");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the location of the camera.
         *
         * @param point The location to set.
         * @return The current camera instance.
         */
        public Builder setLocation(Point point) {
            this.camera.p0 = point;
            return this;
        }

        /**
         * Sets the forward and upward directions of the camera.
         *
         * @param vTo The forward direction.
         * @param vUp The upward direction.
         * @return The current camera instance.
         * @throws IllegalArgumentException If the directions are not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo have to be orthogonal to vUp");
            }
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Constructs the camera object.
         *
         * @return A cloned instance of the camera.
         * @throws CloneNotSupportedException If cloning is not supported.
         */
        public Camera build() throws CloneNotSupportedException {
            String miss = "Missing rendering data";
            String cs = "Camera";
            if (isZero(camera.height))
                throw new MissingResourceException(miss, cs, "height");
            if (isZero(camera.width))
                throw new MissingResourceException(miss, cs, "width");
            if (isZero(camera.distance))
                throw new MissingResourceException(miss, cs, "distance");
            if (camera.imageWriter == null)
                throw new MissingResourceException(miss, cs, "imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(miss, cs, "rayTracer");
            // Check if number of rays is set correctly
            if (camera.numRaysX < 1)
                throw new MissingResourceException(miss, cs, "numRaysX");
            if (camera.numRaysY < 1)
                throw new MissingResourceException(miss, cs, "numRaysY");
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.centerPoint = camera.p0.add(camera.vTo.scale(camera.distance));
            // Cloning and returning the camera
            return (Camera) camera.clone();
        }

    }

}