package hu.akosbalogh;

import hu.akosbalogh.game.GameController;
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
        MapController mapController = new MapController();
        InputController inputController = new InputController();
        MapValidator mapValidator = new MapValidator();
        MapPrinter mapPrinter = new MapPrinter();

        GameController gameController = new GameController(mapController, inputController, mapValidator, mapPrinter);
        gameController.start();
    }
}