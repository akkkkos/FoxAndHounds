package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.game.GameStateService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateServiceTest {

    @Test
    public void buildingMapWithOddNumberForSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            GameStateService gameStateService = new GameStateService(randomWrapper);
            gameStateService.buildNewMap(7);
        });
    }

    @Test
    public void buildingMapWithTooLargeOrTooSmallMapSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            GameStateService gameStateService = new GameStateService(randomWrapper);
            gameStateService.buildNewMap(14);
        });
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            GameStateService gameStateService = new GameStateService(randomWrapper);
            gameStateService.buildNewMap(2);
        });
    }

    @Test
    public void movingHoundRandomlyShouldChangeTheMap() throws Exception {
        RandomWrapper randomWrapper = new RandomWrapper();
        GameStateService gameStateService = new GameStateService(randomWrapper);
        gameStateService.buildNewMap(8);
        char[][] oldMap = gameStateService.getMap().getMapAsChars();
        gameStateService.moveRandomHound();
        char[][] newMap = gameStateService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        //given(randomController.getRandomHound(mapController.getMap().getNumberOfColumns() / 2)).willReturn(1);

        oldMap = newMap;
        gameStateService.moveRandomHound();
        newMap = gameStateService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        gameStateService.moveRandomHound();
        newMap = gameStateService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        gameStateService.moveRandomHound();
        newMap = gameStateService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));
    }

    @Test
    public void movingWithFoxShouldBeAbleToMoveFromOneSideToAnother() throws Exception {
        RandomWrapper randomWrapper = new RandomWrapper();
        GameStateService gameStateService = new GameStateService(randomWrapper);
        gameStateService.buildNewMap(6);
        char[][] oldMap = gameStateService.getMap().getMapAsChars();

        gameStateService.moveFox("ur");
        gameStateService.moveFox("dr");
        gameStateService.moveFox("ur");
        gameStateService.moveFox("dr");
        gameStateService.moveFox("ur");

        char[][] newMap = gameStateService.getMap().getMapAsChars();

        assertFalse(Arrays.deepEquals(oldMap, newMap));

        gameStateService.moveFox("dl");
        gameStateService.moveFox("ul");
        gameStateService.moveFox("dl");
        gameStateService.moveFox("ul");
        gameStateService.moveFox("dl");

        newMap = gameStateService.getMap().getMapAsChars();

        assertTrue(Arrays.deepEquals(oldMap, newMap));
    }

    @Test
    public void runningGettersWithoutMapInitializationFirstShouldResultInException() {
        RandomWrapper randomWrapper = new RandomWrapper();
        GameStateService gameStateService = new GameStateService(randomWrapper);

        assertThrows(Exception.class, () -> {
            gameStateService.getMap();
        });

        assertThrows(Exception.class, () -> {
            gameStateService.getFoxPosition();
        });
    }

    @Test
    public void movingCharactersWithoutMapInitializationFirstShouldResultInException() {
        RandomWrapper randomWrapper = new RandomWrapper();
        GameStateService gameStateService = new GameStateService(randomWrapper);

        assertThrows(Exception.class, () -> {
            gameStateService.moveFox("ur");
        });

        assertThrows(Exception.class, () -> {
            gameStateService.moveRandomHound();
        });
    }
}
