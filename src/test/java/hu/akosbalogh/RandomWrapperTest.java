package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomWrapperTest {
    @Test
    public void gettingRandomHoundShouldReturnBetweenTheCorrectNumbers() {
        RandomWrapper randomWrapper = new RandomWrapper();
        int n = randomWrapper.getRandomHound(4);
        assertTrue(n >= 0 && n <= 3);
    }

    @Test
    public void gettingRandomHoundMoveShouldReturnBetweenTheCorrectNumbers() {
        RandomWrapper randomWrapper = new RandomWrapper();
        int n = randomWrapper.getRandomHoundMove();
        assertTrue(n == 0 || n == 1);
    }
}
