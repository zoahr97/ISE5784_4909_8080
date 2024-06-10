package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

public class Geometries  implements Intersectable{

    private final List<Intersectable> geometries = new LinkedList<>();

    Geometries(){}

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        this.geometries.addAll(List.of(geometries));

    }

    public List<Point> findIntersections(Ray ray){
        List<Point> Intersection = null;
        for (Intersectable geometry : geometries) {
            List<Point>  i = (geometry.findIntersections(ray));
            if(i!=null){
                if (Intersection==null){
                    Intersection= new ArrayList<>();
                }
                Intersection.addAll(i);
            }
        }
        return Intersection;
    }
}

