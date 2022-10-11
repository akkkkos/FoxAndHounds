package hu.akosbalogh.map.model;


import java.util.Arrays;

/**
 * Map model.
 */
public class Map {
    private char[][] mapAsChars;

    public Map(char[][] map) {
        this.mapAsChars = map;
    }

    public int getNumberOfRows() {
        return mapAsChars.length;
    }

    public int getNumberOfColumns() {
        return mapAsChars[0].length;
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

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        for (int i = 0; i < mapAsChars.length; i++) {
            for (int j = 0; j < mapAsChars[0].length; j++) {
                mapAsString.append(mapAsChars[i][j]);
            }
            mapAsString.append("\n");
        }
        return "Map{\n" +
                mapAsString +
                "\n}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Map map = (Map) o;
        return Arrays.deepEquals(mapAsChars, map.mapAsChars);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(mapAsChars);
    }
}
