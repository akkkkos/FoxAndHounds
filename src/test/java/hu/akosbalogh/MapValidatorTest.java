package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.game.GameStateService;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapValidatorTest {
    MapValidator mapValidator = new MapValidator();
    RandomWrapper randomWrapper = new RandomWrapper();
    GameStateService gameStateService = new GameStateService(randomWrapper);

    @Test
    public void searchFoxColumnIndexShouldReturnCorrectColumnIndex() throws Exception {
        gameStateService.buildNewMap(8);
        gameStateService.moveFox("ur");
        gameStateService.moveFox("ur");

        int foxColumnIndex = mapValidator.searchFoxColumnIndex(gameStateService.getMap());

        assertEquals(foxColumnIndex, gameStateService.getFoxPosition()[1]);
        assertEquals(gameStateService.getMap().getMapAsChars()[5][foxColumnIndex],'F');
    }

    @Test
    public void isHoundWinnerShouldReturnCorrectly() throws Exception {
        gameStateService.buildNewMap(6);

        assertFalse(mapValidator.isHoundWinner(gameStateService.getMap()));

        Map oldMap = gameStateService.getMap();
        char[][] oldMapAsChar = oldMap.getMapAsChars();
        oldMapAsChar[4][1] = 'H';
        oldMap.setMapAsChars(oldMapAsChar);

        assertTrue(mapValidator.isHoundWinner(oldMap));
    }

    @Test
    public void isFoxWinnerShouldReturnCorrectly() throws Exception {
        gameStateService.buildNewMap(6);

        assertFalse(mapValidator.isFoxWinner(gameStateService.getMap()));

        Map oldMap = gameStateService.getMap();
        char[][] oldMapAsChar = oldMap.getMapAsChars();
        oldMapAsChar[0][1] = 'F';
        oldMap.setMapAsChars(oldMapAsChar);

        assertTrue(mapValidator.isFoxWinner(oldMap));
    }

    @Test
    public void searchingForFoxWithoutValidMapShouldResultInException() {
        char[][] charMap = new char[0][0];
        Map map = new Map(charMap);

        assertThrows(Exception.class, () -> {
           mapValidator.searchFoxRowIndex(map);
        });
    }

}
