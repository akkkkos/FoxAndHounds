package hu.akosbalogh.data;

/**
 * Todo
 */
public class ScoreRepository {
    // Todo


    public ScoreRepository() {
    }

    /**
     * Todo.
     *
     * @param name Todo.
     */
    public void increaseScore(String name) {
        //Todo
        //Search if name exists
        //If it exists, increase number, if it does not, create new with value 1
    }

    /**
     * Todo.
     *
     * @return Todo.
     */
    public String[][] getTopFiveHighScores() {
        String[][] result = new String[5][2];
        result[0][0] = "Ákos";
        result[0][1] = "2";
        return result;
    }
}
