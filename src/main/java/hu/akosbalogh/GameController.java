package hu.akosbalogh;

public class GameController {

    private boolean isRunning;
    private MapController mapController;
    private InputController inputController;
    private MapValidator mapValidator;
    private MapPrinter mapPrinter;

    public GameController(MapController mapController, InputController inputController) {
        this.mapController = mapController;
        this.inputController = inputController;
        this.mapValidator = new MapValidator();
        this.mapPrinter = new MapPrinter();
        isRunning = true;
    }

    public void StartGame() {
        while(isRunning)
        {
            mapPrinter.printMap(mapController.getMap());
            String move = inputController.GetUserInput(mapController.getMap());
            mapController.moveWolf(move);

            if (mapValidator.isWolfWinner(mapController.getMap())) {
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
