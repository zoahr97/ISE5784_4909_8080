package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint(){
        double sqrt=Math.sqrt(3);
        Ray ray = new Ray(new Point(1/sqrt,1/sqrt,1/sqrt),new Vector(1/sqrt,1/sqrt,1/sqrt));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test getPoint with a correct ray negative value
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"negative value");
        // TC02: Test getPoint with a correct ray positeve value
        assertEquals(new Point(1,1,1),ray.getPoint(sqrt-1),"positeve value");
        // =============== Boundary Values Tests ==================
        // TC03: Test getPoint with a correct ray zero value
        assertEquals(new Point(1/sqrt,1/sqrt,1/sqrt),ray.getPoint(0),"zero value");
    }
}