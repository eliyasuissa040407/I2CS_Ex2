/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        StdDraw.setCanvasSize(w * 4, h * 4);
        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(-0.5, h - 0.5);
        StdDraw.enableDoubleBuffering();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int colorValue = map.getPixel(x, y);
                StdDraw.setPenColor(colorValue, colorValue, colorValue);
                StdDraw.filledSquare(x, y, 0.5);
            }
        }
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;
        try {
            File myFile = new File(mapFileName);
            Scanner s1 = new Scanner(myFile);
            int rows = 0;
            int cols = 0;

            while (s1.hasNextLine()) {
                String rowText = s1.nextLine().trim();
                if (rowText.length() > 0) {
                    if (rows == 0) {
                        cols = rowText.split("\\s+").length;
                    }
                    rows++;
                }
            }
            s1.close();

            if (rows > 0 && cols > 0) {
                ans = new Map(cols, rows, 0);
                Scanner s2 = new Scanner(myFile);
                for (int j = 0; j < rows; j++) {
                    for (int i = 0; i < cols; i++) {
                        if (s2.hasNextInt()) {
                            ans.setPixel(i, j, s2.nextInt());
                        }
                    }
                }
                s2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

}

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        try {
            FileWriter myWriter = new FileWriter(mapFileName);
            for (int j = 0; j < map.getHeight(); j++) {
                for (int i = 0; i < map.getWidth(); i++) {
                    // כותבים את המספר ורווח אחריו
                    myWriter.write(map.getPixel(i, j) + " ");
                }
                myWriter.write("\n"); // יורדים שורה בסוף כל שורת פיקסלים
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] a) {
        int w = java.awt.Color.WHITE.getRGB();
        int b = java.awt.Color.BLACK.getRGB();
        int r = java.awt.Color.RED.getRGB();

        int[][] maze = {
                {b, b, b, b, b, b},
                {b, w, w, w, r, b},
                {b, w, b, b, w, b},
                {b, w, w, w, w, b},
                {b, b, b, b, b, b}
        };

        Map2D myMap = new Map(maze);
        String fileName = "myMaze.txt";


        saveMap(myMap, fileName);
        Map2D loaded = Ex2_GUI.loadMap(fileName);
        if (loaded != null) {
            Ex2_GUI.drawMap(loaded);
        }
    }
    /// ///////////// Private functions ///////////////

