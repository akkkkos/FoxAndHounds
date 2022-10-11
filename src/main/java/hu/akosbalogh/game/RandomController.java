package hu.akosbalogh.game;

import java.util.Random;

/**
 * RandomController class for handling randomized returns.
 */

public class RandomController {
    private final Random rand;

    public RandomController() {
        this.rand = new Random();
    }

    public int getRandomHound(int numberOfHounds) {
        return rand.nextInt(numberOfHounds);
    }

    public int getRandomHoundMove() {
        return rand.nextInt(2);
    }
}
