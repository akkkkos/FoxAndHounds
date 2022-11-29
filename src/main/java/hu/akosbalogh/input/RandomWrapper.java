package hu.akosbalogh.input;

import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * RandomController class for handling randomized returns.
 */
@Component
public class RandomWrapper {
    private final Random rand;

    public RandomWrapper() {
        this.rand = new Random();
    }

    public int getRandomHound(int numberOfHounds) {
        return rand.nextInt(numberOfHounds);
    }

    public int getRandomHoundMove() {
        return rand.nextInt(2);
    }
}
