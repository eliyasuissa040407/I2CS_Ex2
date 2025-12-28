import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Index2D.
 * This class ensures that coordinates, distances, and equality logic work correctly.
 */
class Index2DTest {

    @Test
    void testConstructorsAndGetters() {
        // Test basic constructor
        Index2D p1 = new Index2D(5, 10);
        assertEquals(5, p1.getX());
        assertEquals(10, p1.getY());

        Index2D p2 = new Index2D(p1);
        assertEquals(5, p2.getX());
        assertEquals(10, p2.getY());
    }

    @Test
    void testDistance2D() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);

       assertEquals(5.0, p1.distance2D(p2), 0.001);

        // Test distance to itself
        assertEquals(0.0, p1.distance2D(p1), 0.001);

        assertThrows(RuntimeException.class, () -> p1.distance2D(null));
    }

    @Test
    void testToString() {
        Index2D p1 = new Index2D(7, 8);
        assertEquals("7,8", p1.toString());
    }

    @Test
    void testEquals() {
        Index2D p1 = new Index2D(10, 20);
        Index2D p2 = new Index2D(10, 20);
        Index2D p3 = new Index2D(20, 10);
        String notAPoint = "10,20";

        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(notAPoint));
    }
}