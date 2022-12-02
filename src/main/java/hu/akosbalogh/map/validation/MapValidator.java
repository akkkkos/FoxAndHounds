package hu.akosbalogh.map.validation;

import hu.akosbalogh.map.model.Map;
import org.springframework.stereotype.Service;

/**
 * MapValidator.
 */
@Service
public class MapValidator {
    /**
     * Is specified space available with given map, starting point and move.
     *
     * @param map  Map.
     * @param srI  Starting Row Index.
     * @param scI  Starting Column Index.
     * @param move The Specified Move.
     * @return Returns true if available.
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

        for (int i = 0; i < map.getNumberOfColumns(); i++) {
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
    public boolean isHoundWinner(Map map) throws Exception {
        return foxHasNoAvailableSpace(map);
    }

    private boolean foxHasNoAvailableSpace(Map map) throws Exception {
        int numberOfUnavailableMoves = 0;
        int foxColumnIndex = searchFoxColumnIndex(map);
        int foxRowIndex = searchFoxRowIndex(map);

        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "ur")) {
            numberOfUnavailableMoves++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "ul")) {
            numberOfUnavailableMoves++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "dr")) {
            numberOfUnavailableMoves++;
        }
        if (!isSpecifiedSpaceAvailable(map, foxRowIndex, foxColumnIndex, "dl")) {
            numberOfUnavailableMoves++;
        }

        return numberOfUnavailableMoves == 4;
    }

    /**
     * Checks of no valid move can be made as a hound anymore.
     *
     * @param houndPositions The row and column indexes of the hounds on the map.
     * @param map            The map it should check for valid moves.
     * @return Returns true if no valid move can be made.
     */
    public boolean noValidMoveCanBeMadeAsHound(int[][] houndPositions, Map map) {
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
     * Searches for the fox's row index on a map.
     *
     * @param map The given map the function should search on.
     * @return Returns the fox's row index the map.
     */
    public int searchFoxRowIndex(Map map) throws Exception {

        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'F') {
                    return i;
                }
            }
        }
        throw new Exception("No Fox on map.");

    }

    /**
     * Searches for the fox's column index on a map.
     *
     * @param map The given map the function should search on.
     * @return Returns the fox's column index the map.
     */
    public int searchFoxColumnIndex(Map map) throws Exception {

        for (int i = 0; i < map.getNumberOfRows(); i++) {
            for (int j = 0; j < map.getNumberOfColumns(); j++) {
                if (map.getMapAsChars()[i][j] == 'F') {
                    return j;
                }
            }
        }
        throw new Exception("No Fox on map.");

    }
}
