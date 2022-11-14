package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.game.GameController;
import hu.akosbalogh.game.RandomController;
import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameControllerTest {
    RandomController randomController = new RandomController();
    MapValidator mapValidator = new MapValidator();
    MapPrinter mapPrinter = new MapPrinter();
    ScoreRepository scoreRepository = new ScoreRepository();

    public GameControllerTest() throws SQLException {
    }

    @Test
    public void startingNormalGameShouldNotResultInException() {
        String input = "Ákos\nstart\n6\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter, scoreRepository);

        assertDoesNotThrow(() -> {gameController.start();});
    }

    @Test
    public void movingAtLeastTwiceInGameShouldNotResultInException() {
        String input = "Ákos\nstart\n8\nmove ur\nmove dl\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();

        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter, scoreRepository);


        assertDoesNotThrow(() -> {gameController.start();});
    }

    @Test
    public void allKnownCommandsShouldWork() {
        String input = "Ákos\n" +
                "move\n" +
                "commands\n" +
                "test\n" +
                "start\n" +
                "8\n" +
                "move dl\n" +
                "move ur\n" +
                "scores\n" +
                "exit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter, scoreRepository);


        assertDoesNotThrow(() -> {gameController.start();});
    }
}
