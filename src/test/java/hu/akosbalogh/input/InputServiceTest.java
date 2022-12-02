package hu.akosbalogh.input;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputServiceTest {
    @Test
    public void getUserInputShouldNotResultInException() {
        String input = "start\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputService inputService = new InputService();

        assertDoesNotThrow(() -> {
            inputService.getUserInput();
        });
    }

    @Test
    public void getUserInputShouldReturnCorrectValues() {
        String input = "Ákos\nexit\nstart\nmove\ncommands\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputService inputService = new InputService();

        assertEquals(inputService.getUserInput(), "unknown");
        assertEquals(inputService.getUserInput(), "exit");
        assertEquals(inputService.getUserInput(), "start");
        assertEquals(inputService.getUserInput(), "move");
        assertEquals(inputService.getUserInput(), "commands");
    }

    @Test
    public void userShouldOnlyBeAbleToPickCorrectMapSize() {
        String input = "2\n14\n7\n8\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputService inputService = new InputService();
        int mapSize = inputService.getMapSizeFromUser();
        assertEquals(mapSize, 8);
    }

    @Test
    public void isUserMoveCorrectFormatShouldReturnCorrectly() {
        InputService inputService = new InputService();
        assertFalse(inputService.isUserMoveCorrectFormat("testtesttesttesttest"));
        assertFalse(inputService.isUserMoveCorrectFormat("move ar"));
        assertFalse(inputService.isUserMoveCorrectFormat("move ua"));
        assertTrue(inputService.isUserMoveCorrectFormat("move ur"));
    }
}
