package hu.akosbalogh;

/**
 * Fox and Hounds game controller service.
 */
public class GameController {

    private boolean isRunning;
    private final MapController mapController;
    private final InputController inputController;
    private final MapValidator mapValidator;
    private final MapPrinter mapPrinter;

    public GameController() throws Exception {
        this.inputController = new InputController();
        int mapSize = inputController.getUserInputBeforeGame();
        this.mapController = new MapController(mapSize);
        this.mapValidator = new MapValidator();
        this.mapPrinter = new MapPrinter();
        isRunning = true;
    }

    /**
     * Start a Fox and Hounds game in the console with the specified Map and Input controllers.
     */
    public void startGame() {
        while (isRunning) {
            mapPrinter.printMap(mapController.getMap());
            String input = inputController.getUserInputForGame(mapController.getMap());
            mapController.moveFox(input);

            if (mapValidator.isFoxWinner(mapController.getMap())) {
                mapPrinter.printMap(mapController.getMap());
                System.out.println("Win!");
                isRunning = false;
            } else {

                mapController.moveRandomHound();
                System.out.println("\n\nMoving Random Hound...");

                if (mapValidator.isHoundWinner(mapController.getMap())) {
                    mapPrinter.printMap(mapController.getMap());
                    System.out.println("Lose!");
                    isRunning = false;
                }
            }
        }
        System.out.println("End Of Game.");
    }
}
