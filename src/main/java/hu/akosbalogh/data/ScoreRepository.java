package hu.akosbalogh.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;


/**
 * Fox and Hounds game Database for user's scores and names.
 */
@Repository
public class ScoreRepository {
    private final Connection connection;

    public ScoreRepository() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
    }

    public ScoreRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Increase the players score in the database.
     *
     * @param name The player's username.
     */
    public void increaseScore(String name) {
        String query = "UPDATE PLAYERS SET SCORE = SCORE + 1 WHERE NAME = '" + name + "';";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decrease the players score in the database.
     *
     * @param name The player's username.
     */
    public void decreaseScore(String name) {
        String query = "UPDATE PLAYERS SET SCORE = SCORE - 1 WHERE NAME = '" + name + "';";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the top 5 highest scores from database.
     *
     * @return The top five players in string table form.
     */
    public String[][] getTopFiveHighScores() {
        String[][] result = new String[5][2];

        String query = "SELECT * FROM PLAYERS ORDER BY SCORE DESC LIMIT 5";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int i = 0;
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String score = resultSet.getString("SCORE");

                result[i][0] = name;
                result[i][1] = score;

                i++;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * Logs in the player to the database. If the user already exists, it doesn't do anything,
     * but if it's a new user, it creates a new record.
     *
     * @param userName The given user's username.
     */
    public void loginPlayer(String userName) {
        String query = "SELECT * FROM PLAYERS WHERE PLAYERS.NAME = '" + userName + "';";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                String insertQuery = "INSERT INTO PLAYERS (NAME, SCORE) VALUES (?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, userName);
                preparedStatement.setInt(2, 0);
                preparedStatement.executeUpdate();
                preparedStatement.close();

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the given user from the database if it exists in it.
     *
     * @param userName The given user's username.
     */
    public void removePlayer(String userName) {
        String query = "SELECT * FROM PLAYERS WHERE PLAYERS.NAME = '" + userName + "';";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String deleteQuery = "DELETE FROM PLAYERS WHERE NAME = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, userName);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
