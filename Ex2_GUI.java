import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Ex2_GUI {

    public static void drawMap(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        // הגדלת חלון התצוגה כדי שיהיה ברור
        StdDraw.setCanvasSize(600, 400);
        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(-0.5, h - 0.5);
        StdDraw.enableDoubleBuffering();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int rgb = map.getPixel(x, y);
                StdDraw.setPenColor(new Color(rgb));
                StdDraw.filledSquare(x, y, 0.5);
            }
        }
        StdDraw.show();
    }

    public static Map2D loadMap(String mapFileName) {
        try {
            Scanner sc = new Scanner(new File(mapFileName));
            if (!sc.hasNextInt()) return null;
            int w = sc.nextInt();
            int h = sc.nextInt();
            Map2D ans = new Map(w, h, 0);

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (sc.hasNextInt()) {
                        ans.setPixel(x, y, sc.nextInt());
                    }
                }
            }
            sc.close();
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveMap(Map2D map, String mapFileName) {
        try {
            FileWriter myWriter = new FileWriter(mapFileName);
            myWriter.write(map.getWidth() + " " + map.getHeight() + "\n");

            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    myWriter.write(map.getPixel(x, y) + " ");
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {
        int[] colors = {
                Color.RED.getRGB(), Color.GREEN.getRGB(), Color.BLUE.getRGB(),
                Color.YELLOW.getRGB(), Color.MAGENTA.getRGB(), Color.ORANGE.getRGB()
        };

        int width = 300;
        int height = 200;
        Map2D myMap = new Map(width, height, 0);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int col = x / (width / 3);
                int row = y / (height / 2);
                int colorIndex = row * 3 + col;

                if (colorIndex > 5) colorIndex = 5;

                myMap.setPixel(x, y, colors[colorIndex]);
            }
        }

        String fileName = "six_colors_map.txt";
        saveMap(myMap, fileName);

        Map2D loaded = loadMap(fileName);
        if (loaded != null) {
            drawMap(loaded);
        }
    }
}