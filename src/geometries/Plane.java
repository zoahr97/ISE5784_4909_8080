package geometries;



import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    private final Point q;
    private final Vector normal;

    public Plane(Point vertex, Point vertex1, Point vertex2) {/*ToDo implement const*/
        this.q = vertex;
        this.normal = null;
    }
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }


    public Vector getNormal() {
        return normal;/*ToDo calculate normal*/

    }
    public Vector getNormal(Point vertex) {
        return normal;
    }
}
