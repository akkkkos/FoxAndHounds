package hu.akosbalogh;

import java.util.Arrays;

public class Map {

    private final int numberOfRows;
    private final int numberOfColumns;
    private final char[][] map;

    public Map(int numberOfRows, int numberOfColumns, char[][] map) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.map = map;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public char[][] getMap() {
        return deepCopy(map);
    }

    private char[][] deepCopy(char[][] array) {
        char[][] result = null;

        if (array != null) {
            result = new char[array.length][];
            for (int i = 0; i < array.length; i++) {
                result[i] = Arrays.copyOf(array[i], array[i].length);
            }
        }

        return result;
    }

}
