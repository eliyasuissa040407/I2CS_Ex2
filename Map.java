import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    private int[][] _map;
    private int width = 0;
    private int height = 0;
    // edit this class below
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}


    @Override
    public void init(int w, int h, int v) {
        this._map = new int[w][h];
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                this._map[x][y] = v;
            }
        }
    }
    @Override
    public void init(int[][] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("Array cannot be null or empty");
        }
        if (arr[0] == null || arr[0].length == 0) {
            throw new RuntimeException("Array dimensions cannot be zero");
        }

        int w = arr.length;
        int h = arr[0].length;

        for (int i = 0; i < w; i++) {
            if (arr[i] == null || arr[i].length != h) {
                throw new RuntimeException("Ragged array detected: all rows must have the same length");
            }
        }

        this._map = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                this._map[x][y] = arr[x][y];
            }
        }
    }


    @Override
    public int[][] getMap() {
        int w = getWidth();
        int h = getHeight();
        int[][] ans = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                ans[x][y] = this._map[x][y];
            }
        }
        return ans;
    }

    @Override
    public int getWidth() {
        if (this._map == null) return 0;
        return this._map.length;
    }

    @Override
    public int getHeight() {
        if (this._map == null || this._map.length == 0) return 0;
        return this._map[0].length;
    }

    @Override
    public int getPixel(int x, int y) {
        return this._map[x][y];
    }

    @Override
    public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(), p.getY());
    }

    @Override
    public void setPixel(int x, int y, int v) {
        this._map[x][y] = v;
    }

    @Override
    public void setPixel(Pixel2D p, int v) {
        this.setPixel(p.getX(), p.getY(), v);
    }

    @Override
    public boolean isInside(Pixel2D p) {
        if (p == null) return false;
        int x = p.getX();
        int y = p.getY();
        return (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        if (p == null) {
            return false;
        }
        return this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight();
    }

    @Override
    public void addMap2D(Map2D p) {
        for(int x = 0; x < p.getWidth(); x++){
            for(int y = 0; y < p.getHeight(); y++){
                _map[x][y] = _map[x][y] + p.getPixel(x, y);
            }
        }

    }

    @Override
    public void mul(double scalar) {
        for(int x = 0; x < this.getWidth(); x++){
            for(int y = 0; y < this.getHeight(); y++){
                this._map[x][y] *= scalar;
            }
        }

    }

    @Override
    public void rescale(double sx, double sy) {
        int newW = (int) (this.getWidth() * sx);
        int newH = (int) (this.getHeight() * sy);

        if (newW <= 0 || newH <= 0) {
            throw new RuntimeException("Dimensions must be positive");
        }

        int[][] newMAP = new int[newW][newH];

        for (int x = 0; x < newW; x++) {
            for (int y = 0; y < newH; y++) {
                int oldX = (int) (x / sx);
                int oldY = (int) (y / sy);

                if (oldX >= getWidth()) { oldX = getWidth() - 1; }
                if (oldY >= getHeight()) { oldY = getHeight() - 1; }

                newMAP[x][y] = this._map[oldX][oldY];
            }
        }
        this._map = newMAP;
    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        for(int x = 0; x < this.getWidth(); x++){
            for(int y = 0; y < this.getHeight(); y++){
                Pixel2D pNew = new Index2D(x,y);
                if(center.distance2D(pNew) <= rad ){
                    this._map[x][y] = color;
                }
            }
        }


    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        if (!isInside(p1) || !isInside(p2)) return;

        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);

        if (dx >= dy) {
            if (x1 > x2) { drawLine(p2, p1, color); return; }
            for (int x = x1; x <= x2; x++) {
                double t = (x2 == x1) ? 0 : (double)(x - x1) / (x2 - x1);
                int y = (int) Math.round(y1 + t * (y2 - y1));
                setPixel(x, y, color);
            }
        } else {
            if (y1 > y2) { drawLine(p2, p1, color); return; }
            for (int y = y1; y <= y2; y++) {
                double t = (y2 == y1) ? 0 : (double)(y - y1) / (y2 - y1);
                int x = (int) Math.round(x1 + t * (x2 - x1));
                setPixel(x, y, color);
            }
        }
    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());

        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                this.setPixel(x, y, color);
            }
        }
    }

    @Override
    public boolean equals(Object ob) {
        if (!(ob instanceof Map2D)) {
            return false;
        }
        Map2D p = (Map2D) ob;
        if (!this.sameDimensions(p)) {
            return false;
        }

        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                if (this.getPixel(x, y) != p.getPixel(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
    public int fill(Pixel2D p, int new_v, boolean cyclic) {
        int oldColor = getPixel(p);
        if (oldColor == new_v) return 0;

        int count = 0;
        Queue<Pixel2D> targets = new LinkedList<>();

        setPixel(p, new_v);
        targets.add(p);
        count++;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!targets.isEmpty()) {
            Pixel2D current = targets.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = current.getX() + dx[i];
                int nextY = current.getY() + dy[i];

                if (cyclic) {
                    nextX = (nextX + getWidth()) % getWidth();
                    nextY = (nextY + getHeight()) % getHeight();
                }

                if (nextX >= 0 && nextX < getWidth() && nextY >= 0 && nextY < getHeight()) {
                    if (getPixel(nextX, nextY) == oldColor) {
                        setPixel(nextX, nextY, new_v);
                        targets.add(new Index2D(nextX, nextY));
                        count++;
                    }
                }
            }
        }
        return count;
    }


	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
    @Override
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
        Map2D distMap = allDistance(p1, obsColor, cyclic);

        int totalDist = distMap.getPixel(p2);
        if (totalDist == -1) return null;

        Pixel2D[] path = new Pixel2D[totalDist + 1];
        Pixel2D current = p2;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int d = totalDist; d >= 0; d--) {
            path[d] = current;
            if (d == 0) break;

            for (int[] dir : directions) {
                int nx = current.getX() + dir[0];
                int ny = current.getY() + dir[1];

                if (cyclic) {
                    nx = (nx + getWidth()) % getWidth();
                    ny = (ny + getHeight()) % getHeight();
                }

                if (isInside(new Index2D(nx, ny)) && distMap.getPixel(nx, ny) == d - 1) {
                    current = new Index2D(nx, ny);
                    break;
                }
            }
        }
        return path;
    }
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D distMap = new Map(this.getWidth(), this.getHeight(), -1);
        if (start == null || !isInside(start) || getPixel(start) == obsColor) return distMap;

        Queue<Pixel2D> queue = new LinkedList<>();
        distMap.setPixel(start, 0);
        queue.add(start);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Pixel2D current = queue.poll();
            int currentDist = distMap.getPixel(current);

            for (int[] dir : directions) {
                int nx = current.getX() + dir[0];
                int ny = current.getY() + dir[1];

                if (cyclic) {
                    nx = (nx + getWidth()) % getWidth();
                    ny = (ny + getHeight()) % getHeight();
                }

                if (isInside(new Index2D(nx, ny))) {
                    if (getPixel(nx, ny) != obsColor && distMap.getPixel(nx, ny) == -1) {
                        distMap.setPixel(nx, ny, currentDist + 1);
                        queue.add(new Index2D(nx, ny));
                    }
                }
            }
        }
        return distMap;
    }
	////////////////////// Private Methods ///////////////////////

}
