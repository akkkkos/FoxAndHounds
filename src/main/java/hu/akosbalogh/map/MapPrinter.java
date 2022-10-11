package hu.akosbalogh.map;

import hu.akosbalogh.map.model.Map;

/**
 * Map Printer class for Fox and Hounds game.
 */
public class MapPrinter {

    /**
     * Prints a Fox and Hounds game to the console with colors.
     *
     * @param map The given map that should be printed.
     */
    public void printMap(Map map) throws Exception {
        if (map != null) {
            String ansiReset = "\u001B[0m";
            String ansiYellow = "\u001B[33m";
            String ansiBlack = "\u001B[30m";

            char[][] mapToPrint = map.getMapAsChars();
            for (int i = 0; i < map.getNumberOfColumns(); i++) {
                for (int j = 0; j < map.getNumberOfRows(); j++) {
                    if (mapToPrint[i][j] == 'O') {
                        System.out.print(ansiYellow + 'O' + ansiReset);
                    } else if (mapToPrint[i][j] == 'X') {
                        System.out.print(ansiBlack + 'X' + ansiReset);
                    } else {
                        System.out.print(mapToPrint[i][j]);
                    }
                }
                System.out.println();
            }
        } else {
            throw new Exception("Map is not correct");
        }
    }
}
