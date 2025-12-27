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
       return 0;
    }

    @Override
    public String toString() {
        String ans = null;

        return ans;
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = true;

        return ans;
    }
}
