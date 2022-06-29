package model;

import view.game.GameView;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GlobalVariables {
    public static int surfaceWidth = 2*209/3-32, surfaceHeight = 8*16;
    public static int numberOfTiles = 30;
    public static int numberOfTilesInRow=surfaceWidth/16;
    public static int numberOfTilesInColumn=surfaceHeight/16;
    public int tool6Zelie = 8, arz6Zelie = 16;
    public static int tool=100,arz=90;
    public double epsilon = 0.1;

    public int isEqual(double x, double y) {
        if (abs(x - y) < this.epsilon) return 1;
        return 0;
    }

    public double distanceOfTwoPoints(int x1, int y1, int x2, int y2) {
        return abs(x1 - x2) + abs(y1 - y2);
        //return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String grass="/tile/grass.jpg";
    public static final String desert="/tile/desert.jpg";
    public static final String hill="/tile/hill.JPG";
    public static final String mountain="/tile/mountain.jpg";
    public static final String ocean="/tile/ocean.jpg";
    public static final String plainImage="/tile/plain.jpg";
    public static final String snow="/tile/snow.jpg";
    public static final String tundra="/tile/tundra.jpeg";
}