package hu.akosbalogh;

/*
        String input = "ur";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
 */

import hu.akosbalogh.input.InputController;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputControllerTest {
    @Test
    public void getUserInputShouldNotResultInException() {
        String input = "start\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputController inputController = new InputController();

        assertDoesNotThrow(() -> {
            inputController.getUserInput();
        });
    }

    @Test
    public void getUserInputShouldReturnCorrectValues() {
        String input = "Ákos\nexit\nstart\nmove\ncommands\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputController inputController = new InputController();

        assertEquals(inputController.getUserInput(), "unknown");
        assertEquals(inputController.getUserInput(), "exit");
        assertEquals(inputController.getUserInput(), "start");
        assertEquals(inputController.getUserInput(), "move");
        assertEquals(inputController.getUserInput(), "commands");
    }

    @Test
    public void userShouldOnlyBeAbleToPickCorrectMapSize() {
        String input = "2\n14\n7\n8\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputController inputController = new InputController();
        int mapSize = inputController.getMapSizeFromUser();
        assertEquals(mapSize, 8);
    }

    @Test
    public void isUserMoveCorrectFormatShouldReturnCorrectly() {
        InputController inputController = new InputController();
        assertFalse(inputController.isUserMoveCorrectFormat("testtesttesttesttest"));
        assertFalse(inputController.isUserMoveCorrectFormat("move ar"));
        assertFalse(inputController.isUserMoveCorrectFormat("move ua"));
        assertTrue(inputController.isUserMoveCorrectFormat("move ur"));
    }
}
