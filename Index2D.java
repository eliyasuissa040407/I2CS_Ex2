/**
 * This class represents a 2D point (pixel) on a grid with integer coordinates (x, y).
 * It implements the Pixel2D interface.
 */
public class Index2D implements Pixel2D {

    private int _x, _y;

    /**
     * Constructs a new Index2D with the given x and y coordinates.
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     */
    public Index2D(int x, int y) {
        this._x = x;
        this._y = y;
    }

    /**
     * Copy constructor: constructs a new Index2D from another Pixel2D object.
     * @param other The Pixel2D object to copy from.
     */
    public Index2D(Pixel2D other) {
        if (other != null) {
            this._x = other.getX();
            this._y = other.getY();
        }
    }

    /**
     * Returns the X coordinate of this pixel.
     * @return The x-coordinate.
     */
    @Override
    public int getX() {
        return _x;
    }

    /**
     * Returns the Y coordinate of this pixel.
     * @return The y-coordinate.
     */
    @Override
    public int getY() {
        return _y;
    }

    /**
     * Computes the Euclidean distance between this pixel and another pixel.
     * Formula: sqrt((x1-x2)^2 + (y1-y2)^2)
     * @param p2 The other pixel.
     * @return The 2D Euclidean distance.
     * @throws RuntimeException if p2 is null.
     */
    @Override
    public double distance2D(Pixel2D p2) {
        if (p2 == null) {
            throw new RuntimeException("Error: p2 is null");
        }

        double dx = this.getX() - p2.getX();
        double dy = this.getY() - p2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns a string representation of this pixel in the format "x,y".
     * @return A string representation of the coordinate.
     */
    @Override
    public String toString() {
        return _x + "," + _y;
    }

    /**
     * Compares this pixel to another object for equality.
     * Two pixels are considered equal if they have the same x and y coordinates.
     * @param p The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object p) {
        if (p == null || !(p instanceof Pixel2D)) {
            return false;
        }

        Pixel2D other = (Pixel2D) p;
        return this._x == other.getX() && this._y == other.getY();
    }
}