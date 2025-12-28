import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    private int[][] _map_3_3 = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
    private Map2D _m0, _m1, _m3_3;

    @BeforeEach
    public void setup() {
        _m0 = new Map(10, 10, 0);
        _m1 = new Map(10, 10, 0);
        _m3_3 = new Map(_map_3_3);
    }

    @Test
    @Timeout(value = 2, unit = SECONDS)
    void init() {
        int[][] bigarr = new int[500][500];
        bigarr[10][10] = 7;
        _m1.init(bigarr);

        assertEquals(500, _m1.getWidth());
        assertEquals(500, _m1.getHeight());
        assertEquals(7, _m1.getPixel(10, 10));

        Pixel2D p1 = new Index2D(3, 2);
        int filledCount = _m1.fill(p1, 1, true);
        assertTrue(filledCount > 0, "Fill should return the number of colored pixels");
    }

    @Test
    void testInitWithArray() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);

        assertEquals(_m0.getWidth(), _m1.getWidth());
        assertEquals(_m0.getPixel(1, 1), _m1.getPixel(1, 1));
        assertTrue(_m0.equals(_m1));
    }

    @Test
    void testShortestPath() {
        int[][] maze = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        _m0.init(maze);
        Pixel2D pStart = new Index2D(0, 0);
        Pixel2D pEnd = new Index2D(2, 0);

        Pixel2D[] path = _m0.shortestPath(pStart, pEnd, 1, false);

        assertNotNull(path, "Path should exist by going around the obstacle");
        assertEquals(7, path.length);
    }

    @Test
    void testAllDistance() {
        _m3_3.init(_map_3_3);
        Pixel2D start = new Index2D(0, 0);
        Map2D dists = _m3_3.allDistance(start, 1, false);

        assertEquals(0, dists.getPixel(0, 0));
        assertEquals(-1, dists.getPixel(1, 1));
        assertEquals(4, dists.getPixel(2, 0));
    }

    @Test
    void testEquals() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertTrue(_m0.equals(_m1));

        _m0.setPixel(0, 0, 9);
        assertFalse(_m0.equals(_m1), "Maps should not be equal after changing a pixel");
    }
}