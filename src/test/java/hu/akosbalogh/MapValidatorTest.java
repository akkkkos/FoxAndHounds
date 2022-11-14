package hu.akosbalogh;

import hu.akosbalogh.game.RandomController;
import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapValidatorTest {
    MapValidator mapValidator = new MapValidator();
    RandomController randomController = new RandomController();
    MapController mapController = new MapController(randomController);

    @Test
    public void searchFoxColumnIndexShouldReturnCorrectColumnIndex() throws Exception {
        mapController.buildMap(8);
        mapController.moveFox("ur");
        mapController.moveFox("ur");

        int foxColumnIndex = mapValidator.searchFoxColumnIndex(mapController.getMap());

        assertEquals(foxColumnIndex, mapController.getFoxPosition()[1]);
        assertEquals(mapController.getMap().getMapAsChars()[5][foxColumnIndex],'F');
    }

    @Test
    public void isHoundWinnerShouldReturnCorrectly() throws Exception {
        mapController.buildMap(6);

        assertFalse(mapValidator.isHoundWinner(mapController.getMap()));

        Map oldMap = mapController.getMap();
        char[][] oldMapAsChar = oldMap.getMapAsChars();
        oldMapAsChar[4][1] = 'H';
        oldMap.setMapAsChars(oldMapAsChar);

        assertTrue(mapValidator.isHoundWinner(oldMap));
    }

    @Test
    public void isFoxWinnerShouldReturnCorrectly() throws Exception {
        mapController.buildMap(6);

        assertFalse(mapValidator.isFoxWinner(mapController.getMap()));

        Map oldMap = mapController.getMap();
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
