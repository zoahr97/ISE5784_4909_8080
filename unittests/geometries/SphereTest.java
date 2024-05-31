package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        Sphere sphere = new Sphere(new Point(0, 0, 0), 5);

        // ============ Equivalence Partitions Tests ==============//
        // TC01: tests for calculation of normal to the sphere//
        assertEquals(
                new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)),
                "ERROR: The calculation of normal to the Sphere is not calculated correctly");

        // TC02:  Check if The vector is normalized
        assertEquals(
                1, sphere.getNormal(new Point(1, 1, 0)).length(),
                0.000001,
                "Error the vector was not normalized");

    }

        private final Point p001 = new Point(0, 0, 1);
        private final Point p100 = new Point(1, 0, 0);
        private final Vector v001 = new Vector(0, 0, 1);

        @Test
        public void testFindIntersections() {
            Sphere sphere = new Sphere(p100, 1d);
            final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
            final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
            final var exp = List.of(gp1, gp2);
            final Vector v310 = new Vector(3, 1, 0);
            final Vector v110 = new Vector(1, 1, 0);
            final Point p01 = new Point(-1, 0, 0);

            // ============ Equivalence Partitions Tests ==============
            // TC01: Ray's line is outside the sphere (0 points)
            assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

            // TC02: Ray starts before and crosses the sphere (2 points)
            final var result1 = sphere.findIntersections(new Ray(p01, v310))
                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                    .toList();
            assertEquals(2, result1.size(), "Wrong number of points");
            assertEquals(exp, result1, "Ray crosses sphere");

            // TC03: Ray starts inside the sphere (1 point)

            // יצירת כדור עם מרכז שונה מהמקור ורדיוס 1
                Point center = new Point(1, 2, 3);
                Sphere sphere1 = new Sphere(center, 1);

                // נקודה בתוך הכדור
                Point insidePoint = new Point(1.5, 2, 3);

                // כיוון הקרן - מהנקודה בתוך הכדור לנקודה מחוץ לו
                Vector rayDirection = new Vector(1, 0, 0);

                // יצירת הקרן מהנקודה הפנימית וכיוון הקרן
                Ray ray = new Ray(insidePoint, rayDirection);

                // חישוב נקודות החיתוך
                List<Point> intersections = sphere1.findIntersections(ray);

                // חישוב המרכיבים של נקודת החיתוך המצופה
                double x = 1 + Math.sqrt(3); // 1 + ריבוע של 3
                double y = 2;
                double z = 3;

                // בדיקה שיש חיתוך בנקודה ושהנקודה תואמת את המרכיבים שחישבנו
                assertEquals(1, intersections.size(), "Wrong number of intersection points");
                assertEquals(x, intersections.get(0).getX(), 1e-10, "Wrong x-coordinate for intersection point");
                assertEquals(y, intersections.get(0).getY(), 1e-10, "Wrong y-coordinate for intersection point");
                assertEquals(z, intersections.get(0).getZ(), 1e-10, "Wrong z-coordinate for intersection point");



            // TC04: Ray starts after the sphere (0 points)
            final Point pOutside = new Point(2, 0, 0);
            assertNull(sphere.findIntersections(new Ray(pOutside, v310)), "Ray starts after the sphere");

            // =============== Boundary Values Tests ==================
            // **** Group: Ray's line crosses the sphere (but not the center)
            // TC11: Ray starts at sphere and goes inside (1 point)
            final Point pOnSphere = new Point(1, 1, 0);
            final Point gp4 = new Point(2, 2, 0);
            final var result3 = sphere.findIntersections(new Ray(pOnSphere, v110))
                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOnSphere)))
                    .toList();
            assertEquals(1, result3.size(), "Wrong number of points");
            assertEquals(List.of(gp4), result3, "Ray starts at sphere and goes inside");

            // TC12: Ray starts at sphere and goes outside (0 points)
            final var result4 = sphere.findIntersections(new Ray(pOnSphere, v310));
            assertNull(result4, "Ray starts at sphere and goes outside");

            // **** Group: Ray's line goes through the center
            // TC13: Ray starts before the sphere (2 points)
            final Point pBeforeSphere = new Point(-1, 0, 0);
            final Point gp5 = new Point(0, 0, 0);
            final Point gp6 = new Point(2, 0, 0);
            final var result5 = sphere.findIntersections(new Ray(pBeforeSphere, v001))
                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(pBeforeSphere)))
                    .toList();
            assertEquals(2, result5.size(), "Wrong number of points");
            assertEquals(List.of(gp5, gp6), result5, "Ray starts before the sphere");

            // TC14: Ray starts at sphere and goes inside (1 point)
            final Point pOnSphere2 = new Point(1, 0, 1);
            final Point gp7 = new Point(1, 0, -1);
            final var result6 = sphere.findIntersections(new Ray(pOnSphere2, new Vector(0, 0, -1)))
                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOnSphere2)))
                    .toList();
            assertEquals(1, result6.size(), "Wrong number of points");
            assertEquals(List.of(gp7), result6, "Ray starts at sphere and goes inside");



            // TC16: Ray starts at the center (1 point)
            final Point pCenter = new Point(1, 0, 0);
            final Point gp9 = new Point(1, 0, -1);
            final var result8 = sphere.findIntersections(new Ray(pCenter, new Vector(0, 0, -1)))
                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(pCenter)))
                    .toList();
            assertEquals(1, result8.size(), "Wrong number of points");
            assertEquals(List.of(gp9), result8, "Ray starts at the center");

            // TC17: Ray starts at sphere and goes outside (0 points)
            final Point pOnSphere3 = new Point(1, 0, 1);
            final var result9 = sphere.findIntersections(new Ray(pOnSphere3, new Vector(0, 0, 1)));
            assertNull(result9, "Ray starts at sphere and goes outside");

            // TC18: Ray starts after sphere (0 points)
            final Point pAfterSphere = new Point(1, 0, 2);
            assertNull(sphere.findIntersections(new Ray(pAfterSphere, new Vector(0, 0, 1))), "Ray starts after sphere");

            // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
            // TC19: Ray starts before the tangent point
            final Point pBeforeTangent = new Point(1, -1, 0);
            final var result10 = sphere.findIntersections(new Ray(pBeforeTangent, new Vector(0, 1, 0)));
            assertNull(result10, "Ray starts before the tangent point");

            // TC20: Ray starts at the tangent point
            final Point pTangent = new Point(1, 1, 0);
            final var result11 = sphere.findIntersections(new Ray(pTangent, new Vector(0, 1, 0)));
            assertNull(result11, "Ray starts at the tangent point");

            // TC21: Ray starts after the tangent point
            final Point pAfterTangent = new Point(1, 2, 0);
            final var result12 = sphere.findIntersections(new Ray(pAfterTangent, new Vector(0, 1, 0)));
            assertNull(result12, "Ray starts after the tangent point");

            // **** Group: Special cases
            // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
            final Point pOrthogonal = new Point(2, 0, 0);
            final var result13 = sphere.findIntersections(new Ray(pOrthogonal, new Vector(0, 0, 1)));
            assertNull(result13, "Ray is orthogonal to ray start to sphere's center line");
        }

}