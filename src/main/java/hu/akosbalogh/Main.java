package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.game.GameController;
import hu.akosbalogh.game.RandomController;
import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;

/**
 * The Main class.
 */
public class Main {
    /**
     * The Main method.
     *
     * @param args Input arguments.
     * @throws Exception Exception.
     */
    public static void main(String[] args) throws Exception {
        RandomController randomController = new RandomController();
        MapController mapController = new MapController(randomController);
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();
        ScoreRepository scoreRepository = new ScoreRepository();

        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter, scoreRepository);
        gameController.start();
    }
}