package hu.akosbalogh;

import hu.akosbalogh.game.RandomController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomControllerTest {
    @Test
    public void gettingRandomHoundShouldReturnBetweenTheCorrectNumbers() {
        RandomController randomController = new RandomController();
        int n = randomController.getRandomHound(4);
        assertTrue(n >= 0 && n <= 3);
    }

    @Test
    public void gettingRandomHoundMoveShouldReturnBetweenTheCorrectNumbers() {
        RandomController randomController = new RandomController();
        int n = randomController.getRandomHoundMove();
        assertTrue(n == 0 || n == 1);
    }
}
