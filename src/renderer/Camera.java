package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera implements Cloneable {
    private Point position; // Camera location
    private Vector vUp; // Upward direction
    private Vector vTo; // Forward direction
    private Vector vRight; // Right direction
    private Point centerPoint;
    private double height = 0;
    private double width = 0;
    private double distance = 0;
    private int antiAliasing = 1
            ;
    public Camera setAntiAliasing(int nRays){
        antiAliasing = nRays;
        return this;
    }
    public Point getPosition() {
        return position;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public Vector getvUp() {
        return vUp;
    }

    public double getDistance() {
        return distance;
    }

    public double getWidth() {
        return width;
    }

    public void setLocation(Point point) {
        position = point;
    }

    public void setDirection(Vector vTo, Vector vUp) {
        if (vTo.dotProduct(vUp) == 0) {
            this.vTo = vTo.normalize();
            this.vUp = vUp.normalize();
        }
        return;
    }
/*
    public void setVpSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
    */

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    /*
    public void setVpDistance(double distance) {
        this.distance = distance;
    }
    */

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        // every time that we change the distance from the view plane
        // we will calculate the center point of the view plane again
        centerPoint = position.add(vTo.scale(this.distance));
        return this;
    }
    private Camera() {
    }
    /**
     * Constructs a camera with the given location, forward direction, and upward direction.
     * The upward direction must be orthogonal to the forward direction.
     *
     * @param p0  The location of the camera.
     * @param vTo The forward direction of the camera.
     * @param vUp The upward direction of the camera.
     * @throws IllegalArgumentException If the upward direction is not orthogonal to the forward direction.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.position = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        /** If two vectors are normalized (i.e., their magnitudes are equal to 1),
         *  then their dot product will result in a vector whose magnitude is between 0 and 1.
         *  Taking the absolute value of this vector will result in a normalized vector.*/
        this.vRight = vTo.crossProduct(vUp);
    }
    public static Builder getBuilder() {
        return new Builder();
    }

    /*public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }
   */
    public static class Builder {
        private Camera camera;

        public Builder() {
        }

        public Builder(Camera camera) {
            this.camera = camera;
        }
    }
    private Point getCenterOfPixel(int nX, int nY, int j, int i) {
        // calculate the ratio of the pixel by the height and by the width of the view plane
        // the ratio Ry = h/Ny, the height of the pixel
        double rY = alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(width / nX);

        // Xj = (j - (Nx -1)/2) * Rx
        double xJ = alignZero((j - ((nX - 1d) / 2d)) * rX);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double yI = alignZero(-(i - ((nY - 1d) / 2d)) * rY);

        Point pIJ = centerPoint;

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        return pIJ;
    }
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        Point centralPixel = getCenterOfPixel(nX, nY, j, i);
        double rY = height / nY / antiAliasing;
        double rX = width / nX / antiAliasing;
        // Variables to store the X and Y offsets of each sub-pixel within the anti-aliasing grid
        double x, y;

        for (int rowNumber = 0; rowNumber < antiAliasing; rowNumber++) {
            for (int colNumber = 0; colNumber < antiAliasing; colNumber++) {
                // Calculate the X and Y offsets for the current sub-pixel
                y = -(rowNumber - (antiAliasing - 1d) / 2) * rY;
                x = (colNumber - (antiAliasing - 1d) / 2) * rX;
                // Calculate the position of the current sub-pixel within the pixel
                Point pIJ = centralPixel;
                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
                // Construct a ray from the camera position to the current sub-pixel
                rays.add(new Ray(position, pIJ.subtract(position)));
            }
        }
        return rays;
    }


}
