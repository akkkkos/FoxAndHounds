package hu.akosbalogh.game;

import java.util.Arrays;
import java.util.Objects;

import hu.akosbalogh.exceptions.MapInitializationException;
import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.validation.MapValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Map Controller for Fox and Hounds game.
 */
@Service
public class GameStateService {

    private final RandomWrapper randomWrapper;
    private Map map;
    private int[][] houndPositions;
    private int[] foxPosition;

    @Autowired
    public GameStateService(RandomWrapper randomWrapper) {
        this.randomWrapper = randomWrapper;
    }

    public GameStateService(Map map, int[][] houndPositions, int[] foxPosition) {
        this.map = map;
        this.houndPositions = houndPositions;
        this.foxPosition = foxPosition;
        this.randomWrapper = new RandomWrapper();
    }

    /**
     * Gets the fox's position on the map.
     *
     * @return Returns the fox's row index and column index.
     * @throws Exception If the map hasn't been built before.
     */
    public int[] getFoxPosition() throws Exception {
        if (map != null) {
            return foxPosition;
        } else {
            throw new MapInitializationException();
        }
    }

    /**
     * Gets the hounds' position on the map.
     *
     * @return Returns the hounds' row indexes and column indexes.
     * @throws Exception If the map hasn't been built before.
     */
    public int[][] getHoundPositions() throws Exception {
        if (map != null) {
            return houndPositions;
        } else {
            throw new MapInitializationException();
        }
    }

    /**
     * Gets the map.
     *
     * @return Returns the map as Map object.
     * @throws Exception If the map hasn't been built before.
     */
    public Map getMap() throws Exception {
        if (map != null) {
            return map;
        } else {
            throw new MapInitializationException();
        }
    }


    /**
     * Builds the map with the size given for a Fox and Hounds game.
     *
     * @param mapSize The size the map should be in.
     * @throws Exception If the given map size is not valid.
     */
    public void buildNewMap(int mapSize) throws Exception {
        if (mapSize % 2 == 0) {
            if (mapSize >= 4 && mapSize <= 12) {

                this.houndPositions = new int[mapSize / 2][2];
                this.foxPosition = new int[2];

                char[][] charMap = new char[mapSize][mapSize];
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (i % 2 == 0) {
                            if (j % 2 == 0) {
                                charMap[i][j] = 'X';
                            } else {
                                charMap[i][j] = 'O';
                            }
                        } else {
                            if (j % 2 != 0) {
                                charMap[i][j] = 'X';
                            } else {
                                charMap[i][j] = 'O';
                            }
                        }
                    }
                }

                for (int i = 1; i < mapSize; i += 2) {
                    charMap[0][i] = 'H';
                    houndPositions[i / 2][0] = 0;
                    houndPositions[i / 2][1] = i;
                }

                charMap[mapSize - 1][0] = 'F';
                foxPosition[0] = mapSize - 1;
                foxPosition[1] = 0;

                this.map = new Map(charMap);
            } else {
                throw new Exception("Out of range for map size");
            }

        } else {
            throw new Exception("Odd Map Size.");
        }
    }

    /**
     * Moves the fox on the map with the given move.
     *
     * @param move The given move to do.
     */

    public void moveFox(String move) throws Exception {
        if (map != null) {
            int foxRowIndex = this.foxPosition[0];
            int foxColumnIndex = this.foxPosition[1];

            char[][] newMap = map.getMapAsChars();

            switch (move) {
                case "ur":
                    newMap[foxRowIndex - 1][foxColumnIndex + 1] = 'F';
                    newMap[foxRowIndex][foxColumnIndex] = 'O';
                    this.foxPosition[0]--;
                    this.foxPosition[1]++;
                    break;
                case "dr":
                    newMap[foxRowIndex + 1][foxColumnIndex + 1] = 'F';
                    newMap[foxRowIndex][foxColumnIndex] = 'O';
                    this.foxPosition[0]++;
                    this.foxPosition[1]++;
                    break;
                case "ul":
                    newMap[foxRowIndex - 1][foxColumnIndex - 1] = 'F';
                    newMap[foxRowIndex][foxColumnIndex] = 'O';
                    this.foxPosition[0]--;
                    this.foxPosition[1]--;
                    break;
                case "dl":
                    newMap[foxRowIndex + 1][foxColumnIndex - 1] = 'F';
                    newMap[foxRowIndex][foxColumnIndex] = 'O';
                    this.foxPosition[0]++;
                    this.foxPosition[1]--;
                    break;
                default:
                    break;
            }

            map.setMapAsChars(newMap);
        } else {
            throw new MapInitializationException();
        }
    }

    /**
     * Moves a hound randomly on the map.
     */
    public void moveRandomHound() throws Exception {
        if (map != null) {
            MapValidator mapValidator = new MapValidator();
            int numberOfHounds = map.getNumberOfColumns() / 2;
            char[][] copyOfMap = map.getMapAsChars();
            boolean validMoveMade = false;

            while (!validMoveMade) {
                int randomHoundIndex = randomWrapper.getRandomHound(numberOfHounds);
                int randomHoundMove = randomWrapper.getRandomHoundMove();
                int randomHoundsRowIndex = houndPositions[randomHoundIndex][0];
                int randomHoundsColumnIndex = houndPositions[randomHoundIndex][1];

                if (mapValidator.noValidMoveCanBeMadeAsHound(houndPositions, map)) {
                    System.out.println("No more moves for hounds");
                    validMoveMade = true;
                }

                if (randomHoundMove == 0) {
                    if (mapValidator.isSpecifiedSpaceAvailable(map, randomHoundsRowIndex, randomHoundsColumnIndex, "dl")) {
                        copyOfMap[randomHoundsRowIndex][randomHoundsColumnIndex] = 'O';
                        copyOfMap[randomHoundsRowIndex + 1][randomHoundsColumnIndex - 1] = 'H';
                        this.houndPositions[randomHoundIndex][0]++;
                        this.houndPositions[randomHoundIndex][1]--;
                        validMoveMade = true;
                    }
                } else {
                    if (mapValidator.isSpecifiedSpaceAvailable(map, randomHoundsRowIndex, randomHoundsColumnIndex, "dr")) {
                        copyOfMap[randomHoundsRowIndex][randomHoundsColumnIndex] = 'O';
                        copyOfMap[randomHoundsRowIndex + 1][randomHoundsColumnIndex + 1] = 'H';
                        this.houndPositions[randomHoundIndex][0]++;
                        this.houndPositions[randomHoundIndex][1]++;
                        validMoveMade = true;
                    }
                }
            }

            map.setMapAsChars(copyOfMap);
        } else {
            throw new MapInitializationException();
        }
    }

    @Override
    public String toString() {
        return "GameStateService{" +
                map.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameStateService that = (GameStateService) o;
        return map.equals(that.map);
    }
}
