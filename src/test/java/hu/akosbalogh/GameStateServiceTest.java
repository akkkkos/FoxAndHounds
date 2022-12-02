package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.game.GameStateService;
import hu.akosbalogh.map.model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GameStateServiceTest {

    @Mock
    private RandomWrapper randomWrapper;
    @Mock
    private Map map;

    private GameStateService gameStateService;

    private final char[][] defaultCharMap4x4 = {
            {'X','H','X','H'},
            {'O','X','O','X'},
            {'X','O','X','O'},
            {'F','X','O','X'}
    };

    @BeforeEach
    public void setUp(){
        gameStateService = new GameStateService(randomWrapper);
    }

    @Test
    public void buildingMapWithOddNumberForSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            gameStateService.buildNewMap(7);
        });
    }

    @Test
    public void buildingMapWithTooLargeOrTooSmallMapSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            gameStateService.buildNewMap(14);
        });
        assertThrows(Exception.class,() -> {
            gameStateService.buildNewMap(2);
        });
    }

    @Test
    public void runningGettersWithoutMapInitializationFirstShouldResultInException() {
        assertThrows(Exception.class, () -> {
            gameStateService.getMap();
        });

        assertThrows(Exception.class, () -> {
            gameStateService.getFoxPosition();
        });
    }

    @Test
    public void movingCharactersWithoutMapInitializationFirstShouldResultInException() {
        assertThrows(Exception.class, () -> {
            gameStateService.moveFox("ur");
        });

        assertThrows(Exception.class, () -> {
            gameStateService.moveRandomHound();
        });
    }

    @Test
    public void toStringShouldPrintCorrectly() throws Exception {
        gameStateService.buildNewMap(4);

        String expectedResult = "GameStateService{" +
                "Map{\n" +
                "XHXH\n" +
                "OXOX\n" +
                "XOXO\n" +
                "FXOX\n" +
                "\n}\n" +
                "}";
        String result = gameStateService.toString();

        assertTrue(result.equals(expectedResult));
    }

    @Test
    public void equalsShouldReturnCorrectly() throws Exception {
        GameStateService gameStateService1 = new GameStateService(randomWrapper);
        gameStateService1.buildNewMap(4);
        GameStateService gameStateService2 = new GameStateService(randomWrapper);
        gameStateService2.buildNewMap(4);

        assertTrue(gameStateService1.equals(gameStateService1));
        assertFalse(gameStateService1.equals(null));
        assertFalse(gameStateService1.equals(Exception.class));
        assertTrue(gameStateService1.equals(gameStateService2));

        gameStateService2.buildNewMap(6);
        assertFalse(gameStateService1.equals(gameStateService2));
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
}
