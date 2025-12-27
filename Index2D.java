public class Index2D implements Pixel2D {
    public Index2D(int w, int h) {
        ;
    }
    public Index2D(Pixel2D other) {
        ;
    }
    private int _x, _y;
    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        if (p2 == null) {
            throw new RuntimeException("Error: p2 is null");
        }

        double dx = this.getX() - p2.getX();
        double dy = this.getY() - p2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return _x + "," + _y;
    }

    @Override
    public boolean equals(Object p) {
        if (p == null || !(p instanceof Pixel2D)) {
            return false;
        }

        Pixel2D other = (Pixel2D) p;
        return this._x == other.getX() && this._y == other.getY();
    }
}
