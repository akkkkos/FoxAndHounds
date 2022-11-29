package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.game.GameService;
import hu.akosbalogh.input.RandomWrapper;
import hu.akosbalogh.input.InputService;
import hu.akosbalogh.map.MapService;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameServiceTest {
    RandomWrapper randomWrapper = new RandomWrapper();
    MapValidator mapValidator = new MapValidator();
    MapPrinter mapPrinter = new MapPrinter();
    ScoreRepository scoreRepository = new ScoreRepository();

    public GameServiceTest() throws SQLException {
    }

    @Test
    public void startingNormalGameShouldNotResultInException() {
        String input = "Ákos\nstart\n6\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapService mapService = new MapService(randomWrapper);
        InputService inputService = new InputService();
        GameService gameService = new GameService(mapService, inputService, mapValidator, mapPrinter, scoreRepository);

        assertDoesNotThrow(() -> {
            gameService.start();});
    }

    @Test
    public void movingAtLeastTwiceInGameShouldNotResultInException() {
        String input = "Ákos\nstart\n8\nmove ur\nmove dl\nexit\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MapService mapService = new MapService(randomWrapper);
        InputService inputService = new InputService();

        GameService gameService = new GameService(mapService, inputService, mapValidator, mapPrinter, scoreRepository);


        assertDoesNotThrow(() -> {
            gameService.start();});
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

        MapService mapService = new MapService(randomWrapper);
        InputService inputService = new InputService();
        GameService gameService = new GameService(mapService, inputService, mapValidator, mapPrinter, scoreRepository);


        assertDoesNotThrow(() -> {
            gameService.start();});
    }
}
