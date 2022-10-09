package hu.akosbalogh;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputControllerTest {
    @Test
    public void specificInputShouldReturnCorrectly() throws Exception {
        MapController mapController = new MapController(8);
        mapController.moveFox("ur");

        InputController inputController = new InputController();

        String input = "ur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ur", result);

        input = "dr";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("dr", result);

        input = "dl";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("dl", result);

        input = "ul";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ul", result);
    }

    @Test
    public void inputShouldOnlyReturnWhenInputIsRightLength() throws Exception {
        MapController mapController = new MapController(8);
        InputController inputController = new InputController();

        String input = "ururur\nur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ur", result);
    }

    @Test
    public void inputShouldOnlyReturnWhenGivenVerticalDirectionIsCorrect() throws Exception {
        MapController mapController = new MapController(8);
        InputController inputController = new InputController();

        String input = "ir\nur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ur", result);
    }

    @Test
    public void inputShouldOnlyReturnWhenGivenHorizontalDirectionIsCorrect() throws Exception {
        MapController mapController = new MapController(8);
        InputController inputController = new InputController();

        String input = "ui\nur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ur", result);
    }

    @Test
    public void moveShouldNotBeMadeIfTargetSpaceIsUnavailable() throws Exception {
        MapController mapController = new MapController(8);
        InputController inputController = new InputController();

        String input = "dl\nur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String result = inputController.getUserInputForGame(mapController.getMap());

        assertEquals("ur", result);
    }


}
