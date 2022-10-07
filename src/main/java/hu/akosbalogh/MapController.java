package hu.akosbalogh;

import java.util.Random;

public class MapController {

    private Map map;

    public MapController(int N) throws Exception {
        this.map = buildMap(N);
    }

    public Map getMap() {
        return map;
    }

    public Map buildMap(int N) throws Exception {
        if (N % 2 == 0) {
            char[][] charMap = new char[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
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

            for (int i = 1; i < N; i += 2) {
                charMap[0][i] = 'H';
            }

            charMap[N - 1][0] = 'W';

            Map map = new Map(N,N, charMap);
            return map;
        }
        else {
            throw new Exception("Incorrect Map Size.");
        }
    }

    public void moveWolf(String move) {
        MapValidator mapValidator = new MapValidator();
        int wolfRowIndex = mapValidator.getWolfRowIndex(map);
        int wolfColumnIndex = mapValidator.getWolfColumnIndex(map);

        char[][] newMap = map.getMapAsChars();

        if (move.equals("ur")) {
            newMap[wolfRowIndex - 1][wolfColumnIndex + 1] = 'W';
            newMap[wolfRowIndex][wolfColumnIndex] = 'O';
        } else if (move.equals("dr")) {
            newMap[wolfRowIndex + 1][wolfColumnIndex + 1] = 'W';
            newMap[wolfRowIndex][wolfColumnIndex] = 'O';
        } else if (move.equals("ul")) {
            newMap[wolfRowIndex - 1][wolfColumnIndex - 1] = 'W';
            newMap[wolfRowIndex][wolfColumnIndex] = 'O';
        } else if (move.equals("dl")) {
            newMap[wolfRowIndex + 1][wolfColumnIndex - 1] = 'W';
            newMap[wolfRowIndex][wolfColumnIndex] = 'O';
        }

        map.setMapAsChars(newMap);
    }

    public void moveRandomHound() {
        MapValidator mapValidator = new MapValidator();

        int numberOfHounds = map.getNumberOfColumns() / 2;
        int[][] houndPositions = new int[numberOfHounds][2];
        char[][] copyOfMap = map.getMapAsChars();
        int n;

        for (int k = 0; k < numberOfHounds; k++) {
            n = 0;

            for (int i = 0; i < map.getNumberOfRows(); i++) {
                for (int j = 0; j < map.getNumberOfColumns(); j++) {
                    if (copyOfMap[i][j] == 'H') n++;
                    if (k+1 == n && copyOfMap[i][j] == 'H') {
                        houndPositions[k][0] = i;
                        houndPositions[k][1] = j;
                        i = map.getNumberOfRows();
                        j = map.getNumberOfColumns();
                    }

                }
            }
        }

        Random rand = new Random();
        boolean validMoveMade = false;

        while (!validMoveMade)
        {
            int randomHoundIndex = rand.nextInt(numberOfHounds);
            int randomHoundMove = rand.nextInt(2);

            if (noValidMoveCanBeMadeAsHound(houndPositions)) {
                System.out.println("No more moves for hounds");
                validMoveMade = true;
            }

            if (randomHoundMove == 0){
                if (mapValidator.isSpecifiedSpaceAvailable(map,houndPositions[randomHoundIndex][0],houndPositions[randomHoundIndex][1],"dl")) {
                    copyOfMap[houndPositions[randomHoundIndex][0]][houndPositions[randomHoundIndex][1]] = 'O';
                    copyOfMap[houndPositions[randomHoundIndex][0] + 1][houndPositions[randomHoundIndex][1] - 1] = 'H';
                    validMoveMade = true;
                }
            } else {
                if (mapValidator.isSpecifiedSpaceAvailable(map,houndPositions[randomHoundIndex][0],houndPositions[randomHoundIndex][1],"dr")) {
                    copyOfMap[houndPositions[randomHoundIndex][0]][houndPositions[randomHoundIndex][1]] = 'O';
                    copyOfMap[houndPositions[randomHoundIndex][0] + 1][houndPositions[randomHoundIndex][1] + 1] = 'H';
                    validMoveMade = true;
                }
            }
        }

        map.setMapAsChars(copyOfMap);
    }

    private boolean noValidMoveCanBeMadeAsHound(int[][] houndPositions) {
        MapValidator mapValidator = new MapValidator();
        boolean validMoveCanBeMade = false;

        for (int[] houndPos: houndPositions) {
            if (mapValidator.isSpecifiedSpaceAvailable(map, houndPos[0], houndPos[1], "dr")) {
                validMoveCanBeMade = true;
            }
            if (mapValidator.isSpecifiedSpaceAvailable(map, houndPos[0], houndPos[1], "dl")) {
                validMoveCanBeMade = true;
            }
        }

        return !validMoveCanBeMade;
    }
}
