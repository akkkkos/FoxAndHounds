package hu.akosbalogh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MapControllerTest {

    @Test
    public void buildingMapWithOddNumberForSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            MapController mapController = new MapController(7);
        });
    }

    @Test
    public void buildingMapWithTooLargeOrTooSmallMapSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            MapController mapController = new MapController(14);
        });
        assertThrows(Exception.class,() -> {
            MapController mapController = new MapController(4);
        });
    }

    @Test
    public void movingHoundRandomlyShouldChangeTheMap() throws Exception {
        MapController mapController = new MapController(8);
        char[][] oldMap = mapController.getMap().getMapAsChars();
        mapController.moveRandomHound();
        char[][] newMap = mapController.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        mapController.moveRandomHound();
        newMap = mapController.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        mapController.moveRandomHound();
        newMap = mapController.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        mapController.moveRandomHound();
        newMap = mapController.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));
    }

    @Test
    public void movingWithFoxShouldBeAbleToMoveFromOneSideToAnother() throws Exception {
        MapController mapController = new MapController(6);
        char[][] oldMap = mapController.getMap().getMapAsChars();

        mapController.moveFox("ur");
        mapController.moveFox("dr");
        mapController.moveFox("ur");
        mapController.moveFox("dr");
        mapController.moveFox("ur");

        char[][] newMap = mapController.getMap().getMapAsChars();

        assertFalse(Arrays.deepEquals(oldMap, newMap));

        mapController.moveFox("dl");
        mapController.moveFox("ul");
        mapController.moveFox("dl");
        mapController.moveFox("ul");
        mapController.moveFox("dl");

        newMap = mapController.getMap().getMapAsChars();

        assertTrue(Arrays.deepEquals(oldMap, newMap));
    }
}
