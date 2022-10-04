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

        if (move.equals("ur") && map.getMap()[srI - 1][scI + 1] != 'O') {
            return false;
        }
        if (move.equals("dr") && map.getMap()[srI + 1][scI + 1] != 'O') {
            return false;
        }
        if (move.equals("ul") && map.getMap()[srI - 1][scI - 1] != 'O') {
            return false;
        }
        if (move.equals("dl") && map.getMap()[srI + 1][scI - 1] != 'O') {
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
            if (map.getMap()[0][i] == 'W') {
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
        int wolfPosY = getWolfPosY(map);
        int wolfPosX = getWolfPosX(map);

        if (isSpecifiedSpaceAvailable(map, wolfPosY, wolfPosX, "ur")) {
            n++;
        }
        if (isSpecifiedSpaceAvailable(map, wolfPosY, wolfPosX, "ul")) {
            n++;
        }
        if (isSpecifiedSpaceAvailable(map, wolfPosY, wolfPosX, "dr")) {
            n++;
        }
        if (isSpecifiedSpaceAvailable(map, wolfPosY, wolfPosX, "dl")) {
            n++;
        }

        return n == 4;
    }

    private int getWolfPosX(Map map) {
        for (int i = 0; i < map.getNumberOfRows() - 1; i++) {
            for (int j = 0; j < map.getNumberOfColumns() - 1; j++) {
                if (map.getMap()[i][j] == 'W') {
                    return j;
                }
            }
        }
        return 0;
    }

    private int getWolfPosY(Map map) {
        for (int i = 0; i < map.getNumberOfRows() - 1; i++) {
            for (int j = 0; j < map.getNumberOfColumns() - 1; j++) {
                if (map.getMap()[i][j] == 'W') {
                    return i;
                }
            }
        }
        return 0;
    }
}
