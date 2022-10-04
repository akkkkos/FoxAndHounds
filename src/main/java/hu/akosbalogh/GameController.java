package hu.akosbalogh;

public class GameController {

    private boolean isRunning;
    private MapController mapController;
    private InputController inputController;
    private MapValidator mapValidator;

    public GameController(MapController mapController, InputController inputController, MapValidator mapValidator) {
        this.mapController = mapController;
        this.inputController = inputController;
        this.mapValidator = mapValidator;
    }

    public void StartGame() {
        while(isRunning)
        {
            inputController.GetUserInput();
            /*
            UpdateMap
            CheckMap
            MoveRandom
            CheckMap
             */
        }
    }
}
