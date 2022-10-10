package hu.akosbalogh;

import hu.akosbalogh.game.GameController;
import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControllerTest {

    @Test
    public void startingNormalGameShouldNotResultInException() throws Exception {
        String input = "name\nstart\n6\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapController mapController = new MapController();
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);


        assertDoesNotThrow(() -> {gameController.start();});
    }

    @Test
    public void movingAtleastTwiceInGameShouldNotResultInException() throws Exception {
        String input = "name\nstart\n8\nmove ur\nmove dl\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapController mapController = new MapController();
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);


        assertDoesNotThrow(() -> {gameController.start();});
    }

}
