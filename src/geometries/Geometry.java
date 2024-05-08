package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Define an interface named Geometry for some geometric body.
 *  @author Dvora Enav and Zohar Tamsut
 */
public interface Geometry {

    public  Vector getNormal(Point p);
}
