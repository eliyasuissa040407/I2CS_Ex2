import java.io.Serializable;
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
        int[][] init = new int[h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                init[i][j] = v;
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
        boolean ans = true;
        if(p.getX() > getHeight()){
            if(p.getY() > getWidth()){
                ans = false;
            }
        }

        return ans;
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
        if(p1 == null || p2 == null) return;
        if(p1.getX() == p2.getX() && p1.getY() == p2.getY()){
            _map[p1.getX()][p1.getY()] = color;
        }
        if(p1.getX() < p2.getX()){

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
        boolean ans = false;

        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
