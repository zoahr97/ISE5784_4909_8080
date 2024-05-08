package geometries;

import primitives.Point;
import primitives.Vector;
/** Cylinder class which represents the location of a cylinder in space
 *  @author Dvora Enav and Zohar Tamsut
 */
public class Cylinder {
    /** the hieght of the Cylinder*/
    private final double height;

    /** Constructor to initialize Sphere based object with a radius and height
     * @param height the height of the Cylinder value
     * */
    public Cylinder(double height) {
        this.height = height;
    }

    public Vector getNormal(Point p){
        return null;
    }

}