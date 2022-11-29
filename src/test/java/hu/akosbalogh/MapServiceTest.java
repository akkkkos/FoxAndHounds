package hu.akosbalogh;

import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.map.MapService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MapServiceTest {

    @Test
    public void buildingMapWithOddNumberForSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            MapService mapService = new MapService(randomWrapper);
            mapService.buildMap(7);
        });
    }

    @Test
    public void buildingMapWithTooLargeOrTooSmallMapSizeShouldResultInException() {
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            MapService mapService = new MapService(randomWrapper);
            mapService.buildMap(14);
        });
        assertThrows(Exception.class,() -> {
            RandomWrapper randomWrapper = new RandomWrapper();
            MapService mapService = new MapService(randomWrapper);
            mapService.buildMap(2);
        });
    }

    @Test
    public void movingHoundRandomlyShouldChangeTheMap() throws Exception {
        RandomWrapper randomWrapper = new RandomWrapper();
        MapService mapService = new MapService(randomWrapper);
        mapService.buildMap(8);
        char[][] oldMap = mapService.getMap().getMapAsChars();
        mapService.moveRandomHound();
        char[][] newMap = mapService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        //given(randomController.getRandomHound(mapController.getMap().getNumberOfColumns() / 2)).willReturn(1);

        oldMap = newMap;
        mapService.moveRandomHound();
        newMap = mapService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        mapService.moveRandomHound();
        newMap = mapService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));

        oldMap = newMap;
        mapService.moveRandomHound();
        newMap = mapService.getMap().getMapAsChars();
        assertFalse(Arrays.deepEquals(oldMap, newMap));
    }

    @Test
    public void movingWithFoxShouldBeAbleToMoveFromOneSideToAnother() throws Exception {
        RandomWrapper randomWrapper = new RandomWrapper();
        MapService mapService = new MapService(randomWrapper);
        mapService.buildMap(6);
        char[][] oldMap = mapService.getMap().getMapAsChars();

        mapService.moveFox("ur");
        mapService.moveFox("dr");
        mapService.moveFox("ur");
        mapService.moveFox("dr");
        mapService.moveFox("ur");

        char[][] newMap = mapService.getMap().getMapAsChars();

        assertFalse(Arrays.deepEquals(oldMap, newMap));

        mapService.moveFox("dl");
        mapService.moveFox("ul");
        mapService.moveFox("dl");
        mapService.moveFox("ul");
        mapService.moveFox("dl");

        newMap = mapService.getMap().getMapAsChars();

        assertTrue(Arrays.deepEquals(oldMap, newMap));
    }

    @Test
    public void runningGettersWithoutMapInitializationFirstShouldResultInException() {
        RandomWrapper randomWrapper = new RandomWrapper();
        MapService mapService = new MapService(randomWrapper);

        assertThrows(Exception.class, () -> {
            mapService.getMap();
        });

        assertThrows(Exception.class, () -> {
            mapService.getFoxPosition();
        });
    }

    @Test
    public void movingCharactersWithoutMapInitializationFirstShouldResultInException() {
        RandomWrapper randomWrapper = new RandomWrapper();
        MapService mapService = new MapService(randomWrapper);

        assertThrows(Exception.class, () -> {
            mapService.moveFox("ur");
        });

        assertThrows(Exception.class, () -> {
            mapService.moveRandomHound();
        });
    }
}
