package hu.akosbalogh.map;

import java.util.Random;

import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.validation.MapValidator;

/**
 * Map Controller for Fox and Hounds game.
 */
public class MapController {

    private Map map;
    private int[][] houndPositions;
    private int[] foxPosition;

    public MapController() {
    }

    /**
     * Todo.
     *
     * @return Todo.
     * @throws Exception Todo.
     */
    public int[] getFoxPosition() throws Exception {
        if (map != null) {
            return foxPosition;
        } else {
            throw new Exception("Map hasn't been initialized");
        }
    }

    /**
     * Todo.
     *
     * @return Todo.
     * @throws Exception Todo.
     */
    public Map getMap() throws Exception {
        if (map != null) {
            return map;
        } else {
            throw new Exception("Map hasn't been initialized");
        }
    }


    /**
     * Todo.
     *
     * @param mapSize Todo.
     * @throws Exception Todo.
     */
    public void buildMap(int mapSize) throws Exception {
        if (mapSize % 2 == 0) {
            if (mapSize >= 6 && mapSize <= 12) {

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

                this.map = new Map(mapSize, mapSize, charMap);
            } else {
                throw new Exception("Out of range for map size");
            }

        } else {
            throw new Exception("Odd Map Size.");
        }
    }

    /**
     * Todo.
     *
     * @param move Todo.
     */

    public void moveFox(String move) throws Exception {
        if (map != null) {
            int foxRowIndex = this.foxPosition[0];
            int foxColumnIndex = this.foxPosition[1];

            char[][] newMap = map.getMapAsChars();

            if (move.equals("ur")) {
                newMap[foxRowIndex - 1][foxColumnIndex + 1] = 'F';
                newMap[foxRowIndex][foxColumnIndex] = 'O';
                this.foxPosition[0]--;
                this.foxPosition[1]++;
            } else if (move.equals("dr")) {
                newMap[foxRowIndex + 1][foxColumnIndex + 1] = 'F';
                newMap[foxRowIndex][foxColumnIndex] = 'O';
                this.foxPosition[0]++;
                this.foxPosition[1]++;
            } else if (move.equals("ul")) {
                newMap[foxRowIndex - 1][foxColumnIndex - 1] = 'F';
                newMap[foxRowIndex][foxColumnIndex] = 'O';
                this.foxPosition[0]--;
                this.foxPosition[1]--;
            } else if (move.equals("dl")) {
                newMap[foxRowIndex + 1][foxColumnIndex - 1] = 'F';
                newMap[foxRowIndex][foxColumnIndex] = 'O';
                this.foxPosition[0]++;
                this.foxPosition[1]--;
            }

            map.setMapAsChars(newMap);
        } else {
            throw new Exception("Map hasn't been initialized");
        }
    }

    /**
     * Todo.
     */
    public void moveRandomHound() throws Exception {
        if (map != null) {
            MapValidator mapValidator = new MapValidator();
            int numberOfHounds = map.getNumberOfColumns() / 2;
            char[][] copyOfMap = map.getMapAsChars();

            Random rand = new Random();
            boolean validMoveMade = false;

            while (!validMoveMade) {
                int randomHoundIndex = rand.nextInt(numberOfHounds);
                int randomHoundMove = rand.nextInt(2);
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
            throw new Exception("Map hasn't been initialized");
        }
    }
}
