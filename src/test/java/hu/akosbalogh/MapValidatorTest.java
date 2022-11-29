package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.MapService;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapValidatorTest {
    MapValidator mapValidator = new MapValidator();
    RandomWrapper randomWrapper = new RandomWrapper();
    MapService mapService = new MapService(randomWrapper);

    @Test
    public void searchFoxColumnIndexShouldReturnCorrectColumnIndex() throws Exception {
        mapService.buildMap(8);
        mapService.moveFox("ur");
        mapService.moveFox("ur");

        int foxColumnIndex = mapValidator.searchFoxColumnIndex(mapService.getMap());

        assertEquals(foxColumnIndex, mapService.getFoxPosition()[1]);
        assertEquals(mapService.getMap().getMapAsChars()[5][foxColumnIndex],'F');
    }

    @Test
    public void isHoundWinnerShouldReturnCorrectly() throws Exception {
        mapService.buildMap(6);

        assertFalse(mapValidator.isHoundWinner(mapService.getMap()));

        Map oldMap = mapService.getMap();
        char[][] oldMapAsChar = oldMap.getMapAsChars();
        oldMapAsChar[4][1] = 'H';
        oldMap.setMapAsChars(oldMapAsChar);

        assertTrue(mapValidator.isHoundWinner(oldMap));
    }

    @Test
    public void isFoxWinnerShouldReturnCorrectly() throws Exception {
        mapService.buildMap(6);

        assertFalse(mapValidator.isFoxWinner(mapService.getMap()));

        Map oldMap = mapService.getMap();
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
