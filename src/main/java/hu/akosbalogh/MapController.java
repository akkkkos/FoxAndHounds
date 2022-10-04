package hu.akosbalogh;

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

            for (int i = 1; i < N - 1; i += 2) {
                charMap[0][i] = 'H';
            }

            charMap[N - 1][0] = 'W';

            Map map = new Map(N,N, charMap);
            return map;
        }
        else {
            throw new Exception("Exception message");
        }
    }
}
