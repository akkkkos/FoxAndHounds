package hu.akosbalogh;

import hu.akosbalogh.game.GameController;
import hu.akosbalogh.game.RandomController;
import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameControllerTest {

    @Test
    public void startingNormalGameShouldNotResultInException() {
        String input = "name\nstart\n6\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        RandomController randomController = new RandomController();
        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);


        assertDoesNotThrow(() -> {gameController.start();});
    }

    @Test
    public void movingAtLeastTwiceInGameShouldNotResultInException() {
        String input = "name\nstart\n8\nmove ur\nmove dl\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        RandomController randomController = new RandomController();
        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);


        assertDoesNotThrow(() -> {gameController.start();});
    }

    @Test
    public void allKnownCommandsShouldWork() {
        String input = "name\n" +
                "move\n" +
                "commands\n" +
                "test\n" +
                "start\n" +
                "8\n" +
                "move dl\n" +
                "move ur\n" +
                "exit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        RandomController randomController = new RandomController();
        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);


        assertDoesNotThrow(() -> {gameController.start();});
    }
}
