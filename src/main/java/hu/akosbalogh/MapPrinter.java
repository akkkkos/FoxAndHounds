package hu.akosbalogh;

public class MapPrinter {
    public void printMap(Map map) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLACK = "\u001B[30m";

        char[][] mapToPrint = map.getMapAsChars();
        for (int i = 0; i < map.getNumberOfColumns(); i++) {
            for (int j = 0; j < map.getNumberOfRows(); j++) {
                if (mapToPrint[i][j] == 'O') {
                    System.out.print(ANSI_YELLOW + 'O' + ANSI_RESET);
                } else if (mapToPrint[i][j] == 'X') {
                    System.out.print(ANSI_BLACK + 'X' + ANSI_RESET);
                }
                else {
                    System.out.print(mapToPrint[i][j]);
                }
            }
            System.out.println("");
        }
    }
}
