package hu.akosbalogh.game;

import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;

/**
 * Fox and Hounds game controller service.
 */
public class GameController {

    private boolean isGameRunning;
    private boolean isAppRunning;
    private final MapController mapController;
    private final InputController inputController;
    private final MapValidator mapValidator;
    private final MapPrinter mapPrinter;
    private String userName;

    public GameController(MapController mapController,
                          InputController inputController,
                          MapValidator mapValidator,
                          MapPrinter mapPrinter) {
        this.mapController = mapController;
        this.inputController = inputController;
        this.mapValidator = mapValidator;
        this.mapPrinter = mapPrinter;
        this.userName = new String();
        isGameRunning = false;
        isAppRunning = true;
    }

    /**
     * Start a Fox and Hounds game in the console with the specified Map and Input controllers.
     */
    public void start() throws Exception {
        System.out.println("Starting app...");
        System.out.println("Hello! Please enter your name: ");
        userName = inputController.getUserNameFromUser();
        System.out.println("\nHello " + userName + "!\nEnter commands for available commands.");

        while (isAppRunning) {
            String input = inputController.getUserInput();

            if (!input.equals("unknown")) {

                performCommandIfItsKnown(input);

                if (isGameRunning) {
                    if (input.startsWith("move")) {
                        boolean isMoveInputCorrect = inputController.isUserMoveCorrectFormat(input);
                        if (isMoveInputCorrect) {
                            boolean isMoveValid = mapValidator.isSpecifiedSpaceAvailable(mapController.getMap(),
                                    mapController.getFoxPosition()[0],
                                    mapController.getFoxPosition()[1],
                                    input.substring(5));
                            if (isMoveValid) {
                                System.out.println("\n\n");
                                performGameStep(input.substring(5));
                            } else {
                                System.out.println("Unavailable move");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Unknown command.");
            }
        }
        System.out.println("Exiting app...");
    }

    private void performGameStep(String move) throws Exception {
        mapController.moveFox(move);

        if (mapValidator.isFoxWinner(mapController.getMap())) {
            mapPrinter.printMap(mapController.getMap());
            System.out.println("Win!");
            isGameRunning = false;
        } else {

            mapController.moveRandomHound();
            System.out.println("\nMoving Random Hound...");

            if (mapValidator.isHoundWinner(mapController.getMap())) {
                mapPrinter.printMap(mapController.getMap());
                System.out.println("Lose!");
                isGameRunning = false;
            }
        }
        mapPrinter.printMap(mapController.getMap());
    }

    private void performCommandIfItsKnown(String input) throws Exception {
        if (input.equals("start")) {
            isGameRunning = true;
            int mapSize = inputController.getMapSizeFromUser();
            mapController.buildMap(mapSize);
            System.out.println("\n\nStarting game...");
            mapPrinter.printMap(mapController.getMap());
        } else if (input.equals("exit")) {
            isGameRunning = false;
            isAppRunning = false;
        } else if (!isGameRunning && input.startsWith("move")) {
            System.out.println("No game is running currently.");
        } else if (input.equals("commands")) {
            System.out.println("Available commands: (start, exit, commands, move ur/ul/dr/dl)");
        }
    }
}
