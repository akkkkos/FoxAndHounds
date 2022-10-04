package hu.akosbalogh;

import hu.akosbalogh.Map;

public class MapPrinter {
    private final int boxWidth;
    private final int boxHeight;
    private final Map map;

    public MapPrinter(int boxWidth, int boxHeight, Map map) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.map = map;
    }

    public static void printMap(Map map) {
        char[][] mapToPrint = map.getMap();
        for (int i = 0; i < map.getNumberOfColumns(); i++) {
            for (int j = 0; j < map.getNumberOfRows(); j++) {
                System.out.print(mapToPrint[i][j]);
            }
            System.out.println("");
        }
    }
}
