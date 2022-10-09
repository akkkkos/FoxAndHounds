package hu.akosbalogh;

/**
 * MapValidator.
 */
public class MapValidator {
    /**
     * IsSpecifiedSpaceAvailable.
     *
     * @param map Map.
     * @param srI Starting Row Index.
     * @param scI Starting Column Index.
     * @param move The Specified Move.
     * @return Is it?
     */
    public boolean isSpecifiedSpaceAvailable(Map map, int srI, int scI, String move) {
        int numberOfRows = map.getNumberOfRows();
        int numberOfColumns = map.getNumberOfColumns();
        char horizontalDirection = move.charAt(1);
        char verticalDirection = move.charAt(0);

        if (horizontalDirection == 'l' && scI == 0) {
            return false;
        }
        if (horizontalDirection == 'r' && scI == numberOfColumns - 1) {
            return false;
        }
        if (verticalDirection == 'd' && srI == numberOfRows - 1) {
            return false;
        }
        if (verticalDirection == 'u' && srI == 0) {
            return false;
        }

        if (move.equals("ur") && map.getMapAsChars()[srI - 1][scI + 1] != 'O') {
            return false;
        }
        if (move.equals("dr") && map.getMapAsChars()[srI + 1][scI + 1] != 'O') {
            return false;
        }
        if (move.equals("ul") && map.getMapAsChars()[srI - 1][scI - 1] != 'O') {
            return false;
        }
        if (move.equals("dl") && map.getMapAsChars()[srI + 1][scI - 1] != 'O') {
            return false;
        }

        return true;
    }

    /**
     * Checks if the Fox won.
     *
     * @param map The map.
     * @return Returns true if the Fox won.
     */
    public boolean isFoxWinner(Map map) {
        for (int i = 1; i < map.getNumberOfColumns() - 1; i += 2) {
            if (map.getMapAsChars()[0][i] == 'F') {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Hounds won.
     *
     * @param map The map.
     * @return Returns true if the Hounds won.
     */
    public boolean isHoundWinner(Map map) {
        return foxHasNoAvailableSpace(map);
    }

    private boolean foxHasNoAvailableSpace(Map map) {
        int n = 0;
        int foxColumnIndex = searchFoxColumnIndex(map);
        int foxRowIndex = searchFoxRowIndex(map);

        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "ur")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "ul")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "dr")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "dl")) {
            n++;
        }

        return n == 4;
    }

    boolean noValidMoveCanBeMadeAsHound(int[][] houndPositions, Map map) {
        boolean validMoveCanBeMade = false;

        for (int[] houndPos : houndPositions) {
            if (isSpecifiedSpaceAvailable(map, houndPos[0], houndPos[1], "dr")) {
                validMoveCanBeMade = true;
            }
            if (isSpecifiedSpaceAvailable(map, houndPos[0], houndPos[1], "dl")) {
                validMoveCanBeMade = true;
            }
        }

        return !validMoveCanBeMade;
    }

    /**
     * Todo.
     *
     * @param map Todo.
     * @return Todo.
     */
    public int searchFoxRowIndex(Map map) {
        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'F') {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Todo.
     *
     * @param map Todo.
     * @return Todo.
     */
    public int searchFoxColumnIndex(Map map) {
        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'F') {
                    return j;
                }
            }
        }
        return 0;
    }
}
