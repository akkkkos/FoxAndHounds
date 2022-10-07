package hu.akosbalogh;

/**
 * The Main class.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MapController map = new MapController(8);
        InputController inputController = new InputController();
        GameController gameController = new GameController(map, inputController);

        gameController.StartGame();
    }
}