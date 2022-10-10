package hu.akosbalogh;

import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapValidatorTest {

    @Test
    public void searchFoxColumnIndexShouldReturnCorrectColumnIndex() throws Exception {

        MapValidator mapValidator = new MapValidator();
        MapController mapController = new MapController();
        mapController.buildMap(8);
        mapController.moveFox("ur");
        mapController.moveFox("ur");

        int foxColumnIndex = mapValidator.searchFoxColumnIndex(mapController.getMap());

        assertEquals(foxColumnIndex, mapController.getFoxPosition()[1]);
        assertEquals(mapController.getMap().getMapAsChars()[5][foxColumnIndex],'F');
    }

    @Test
    public void isHoundWinnerShouldReturnCorrectly() throws Exception {
        MapValidator mapValidator = new MapValidator();
        MapController mapController = new MapController();
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
        MapValidator mapValidator = new MapValidator();
        MapController mapController = new MapController();
        mapController.buildMap(6);

        assertFalse(mapValidator.isFoxWinner(mapController.getMap()));

        Map oldMap = mapController.getMap();
        char[][] oldMapAsChar = oldMap.getMapAsChars();
        oldMapAsChar[0][1] = 'F';
        oldMap.setMapAsChars(oldMapAsChar);

        assertTrue(mapValidator.isFoxWinner(oldMap));
    }
}
