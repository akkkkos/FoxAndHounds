package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ScoreRepositoryTest {
    ScoreRepository scoreRepository = new ScoreRepository();

    public ScoreRepositoryTest() throws SQLException {
    }

    @Test
    public void increasingAndDecreasingUserScoreShouldReturnOK() {
        assertDoesNotThrow(() -> {scoreRepository.increaseScore("Ákos");});
        assertDoesNotThrow(() -> {scoreRepository.decreaseScore("Ákos");});
    }

    @Test
    public void gettingHighScoresShouldReturnOK() {
        assertDoesNotThrow(() -> {scoreRepository.getTopFiveHighScores();});
    }

    @Test
    public void addingAndRemovingUsersShouldReturnOK() {
        assertDoesNotThrow(() -> {scoreRepository.loginPlayer("nonExistingUser");});
        assertDoesNotThrow(() -> {scoreRepository.removePlayer("nonExistingUser");});
    }
}
