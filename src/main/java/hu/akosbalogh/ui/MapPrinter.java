package hu.akosbalogh.ui;

import hu.akosbalogh.model.Map;

public class MapPrinter {
    /*
     private static final String HORIZONTAL_SEPARATOR = "-";
     private static final String VERTICAL_SEPARATOR = " | ";
    */

    private final int boxWidth;
    private final int boxHeight;
    private final Map map;

    public MapPrinter(int boxWidth, int boxHeight, Map map) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.map = map;
    }

    public void printMap(Map map) {
        char[][] mapToPrint = map.getMap();
        for (int i = 0; i < map.getNumberOfColumns(); i++) {
            for (int j = 0; j < map.getNumberOfRows(); j++) {
                System.out.print(mapToPrint[i][j]);
            }
            System.out.println("");
        }
    }
}
