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
     * Checks if the Wolf is winner.
     *
     * @param map The map.
     * @return Is it?
     */
    public boolean isWolfWinner(Map map) {
        for (int i = 1; i < map.getNumberOfColumns() - 1; i += 2) {
            if (map.getMapAsChars()[0][i] == 'W') {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Hounds are the winner.
     *
     * @param map The map.
     * @return Are they?
     */
    public boolean isHoundWinner(Map map) {
        return wolfHasNoAvailableSpace(map);
    }

    private boolean wolfHasNoAvailableSpace(Map map) {
        int n = 0;
        int wolfColumnIndex = getWolfColumnIndex(map);
        int wolfRowIndex = getWolfRowIndex(map);

        if (!isSpecifiedSpaceAvailable(map, wolfRowIndex, wolfColumnIndex, "ur")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, wolfRowIndex, wolfColumnIndex, "ul")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, wolfRowIndex, wolfColumnIndex, "dr")) {
            n++;
        }
        if (!isSpecifiedSpaceAvailable(map, wolfRowIndex, wolfColumnIndex, "dl")) {
            n++;
        }

        return n == 4;
    }

    public int getWolfRowIndex(Map map) {
        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'W') {
                    return i;
                }
            }
        }
        return 0;
    }

    public int getWolfColumnIndex(Map map) {
        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'W') {
                    return j;
                }
            }
        }
        return 0;
    }
}
