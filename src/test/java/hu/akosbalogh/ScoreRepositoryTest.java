package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoreRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    private ScoreRepository scoreRepository;

    private final String[][] highScores = {
            {"Ákos","2"},
            {"Balázs","1"},
            {null,null},
            {null,null},
            {null,null}
    };

    private final String increaseQuery = "UPDATE PLAYERS SET SCORE = SCORE + 1 WHERE NAME = 'Ákos';";
    private final String decreaseQuery = "UPDATE PLAYERS SET SCORE = SCORE - 1 WHERE NAME = 'Ákos';";
    private final String topFiveQuery = "SELECT * FROM PLAYERS ORDER BY SCORE DESC LIMIT 5";
    private final String allWhereNameQuery = "SELECT * FROM PLAYERS WHERE PLAYERS.NAME = 'Ákos';";
    private final String insertQuery = "INSERT INTO PLAYERS (NAME, SCORE) VALUES (?, ?);";
    private final String deleteQuery = "DELETE FROM PLAYERS WHERE NAME = ?";

    public ScoreRepositoryTest() {
    }

    @BeforeEach
    public void setUp() {
        scoreRepository = new ScoreRepository(connection);
    }

    @Test
    public void increasingUserScoreShouldReturnOK() throws SQLException {
        given(connection.createStatement()).willReturn(statement);

        assertDoesNotThrow(() -> scoreRepository.increaseScore("Ákos"));

        verify(statement).executeUpdate(increaseQuery);
        verify(statement).close();
    }

    @Test
    public void decreasingUserScoreShouldReturnOK() throws SQLException {
        given(connection.createStatement()).willReturn(statement);

        assertDoesNotThrow(() -> scoreRepository.decreaseScore("Ákos"));

        verify(statement).executeUpdate(decreaseQuery);
        verify(statement).close();
    }


    @Test
    public void gettingHighScoresShouldReturnOK() throws SQLException {
        given(connection.createStatement()).willReturn(statement);
        given(statement.executeQuery(topFiveQuery)).willReturn(resultSet);
        when(resultSet.next()).thenReturn(true,true,false);
        when(resultSet.getString("NAME")).thenReturn("Ákos","Balázs");
        when(resultSet.getString("SCORE")).thenReturn("2","1");

        String[][] result = scoreRepository.getTopFiveHighScores();

        assertArrayEquals(result[0], highScores[0]);
        assertArrayEquals(result[1], highScores[1]);
        assertArrayEquals(result[2], highScores[2]);
        assertArrayEquals(result[3], highScores[3]);
        assertArrayEquals(result[4], highScores[4]);

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void loginPlayerShouldCreateNewRecordWithNameAndZeroScore() throws SQLException {
        given(connection.createStatement()).willReturn(statement);
        given(statement.executeQuery(allWhereNameQuery)).willReturn(resultSet);
        given(resultSet.next()).willReturn(false);
        given(connection.prepareStatement(insertQuery)).willReturn(preparedStatement);

        assertDoesNotThrow(() -> scoreRepository.loginPlayer("Ákos"));

        verify(preparedStatement).setString(1,"Ákos");
        verify(preparedStatement).setInt(2,0);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void removePlayerShouldRemoveRecordWithExistingName() throws SQLException {
        given(connection.createStatement()).willReturn(statement);
        given(statement.executeQuery(allWhereNameQuery)).willReturn(resultSet);
        given(resultSet.next()).willReturn(true);
        given(connection.prepareStatement(deleteQuery)).willReturn(preparedStatement);

        assertDoesNotThrow(() -> scoreRepository.removePlayer("Ákos"));

        verify(preparedStatement).setString(1,"Ákos");
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void closeConnectionShouldCloseConnection() throws SQLException {
        assertDoesNotThrow(() -> scoreRepository.closeConnection());

        verify(connection).close();
    }
}
