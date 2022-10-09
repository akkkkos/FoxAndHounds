package hu.akosbalogh;

import java.util.Arrays;

/**
 * Todo.
 */
public class Map {

    private final int numberOfRows;
    private final int numberOfColumns;
    private char[][] mapAsChars;

    public Map(int numberOfRows, int numberOfColumns, char[][] map) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.mapAsChars = map;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public char[][] getMapAsChars() {
        return deepCopy(mapAsChars);
    }

    public void setMapAsChars(char[][] mapAsChars) {
        this.mapAsChars = mapAsChars;
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
