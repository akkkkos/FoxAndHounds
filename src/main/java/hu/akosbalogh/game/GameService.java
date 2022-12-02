package hu.akosbalogh.game;

import java.sql.SQLException;

import hu.akosbalogh.data.GameStateRepository;
import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.input.InputService;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Fox and Hounds game controller service.
 */
@Service
public class GameService {

    private boolean isGameRunning;
    private boolean isAppRunning;
    private GameStateService gameStateService;
    private final InputService inputService;
    private final MapValidator mapValidator;
    private final MapPrinter mapPrinter;
    private final ScoreRepository scoreRepository;
    private final GameStateRepository gameStateRepository;
    private String userName;

    @Autowired
    public GameService(GameStateService gameStateService,
                       InputService inputService,
                       MapValidator mapValidator,
                       MapPrinter mapPrinter,
                       ScoreRepository scoreRepository,
                       GameStateRepository gameStateRepository) {
        this.gameStateService = gameStateService;
        this.inputService = inputService;
        this.mapValidator = mapValidator;
        this.mapPrinter = mapPrinter;
        this.scoreRepository = scoreRepository;
        this.gameStateRepository = gameStateRepository;
        this.userName = "";
        isGameRunning = false;
        isAppRunning = true;
    }

    /**
     * Start a Fox and Hounds game in the console with the specified Map and Input controllers.
     */
    public void start() throws Exception {

        System.out.println("Starting app...");
        System.out.println("Hello! Please enter your name: ");
        userName = inputService.getUserNameFromUser();
        scoreRepository.loginPlayer(userName);
        System.out.println("\nHello " + userName + "!\nEnter commands for available commands.");

        while (isAppRunning) {
            String input = inputService.getUserInput();
            if (!input.equals("unknown")) {
                performKnownCommand(input);
                if (isGameRunning) {
                    if (input.startsWith("move")) {
                        boolean isMoveInputCorrectFormat = inputService.isUserMoveCorrectFormat(input);
                        if (isMoveInputCorrectFormat) {
                            boolean isMoveValid = mapValidator.isSpecifiedSpaceAvailable(gameStateService.getMap(),
                                    gameStateService.getFoxPosition()[0],
                                    gameStateService.getFoxPosition()[1],
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
        scoreRepository.closeConnection();
        System.out.println("Exiting app...");
    }

    private void performGameStep(String move) throws Exception {
        gameStateService.moveFox(move);

        if (mapValidator.isFoxWinner(gameStateService.getMap())) {
            System.out.println("\n");
            mapPrinter.printMap(gameStateService.getMap());
            System.out.println("Win!");
            saveWinToDb();
            deleteGameStateIfExists();
            isGameRunning = false;
        } else {

            gameStateService.moveRandomHound();
            System.out.println("\nMoving Random Hound...");

            if (mapValidator.isHoundWinner(gameStateService.getMap())) {
                System.out.println("\n");
                mapPrinter.printMap(gameStateService.getMap());
                System.out.println("Lose!");
                deleteGameStateIfExists();
                isGameRunning = false;
            }
        }
        mapPrinter.printMap(gameStateService.getMap());
    }

    private void performKnownCommand(String input) throws Exception {
        if (input.equals("start")) {
            isGameRunning = true;
            int mapSize = inputService.getMapSizeFromUser();
            gameStateService.buildNewMap(mapSize);
            System.out.println("\n\nStarting game...");
            mapPrinter.printMap(gameStateService.getMap());
        } else if (input.equals("exit")) {
            isGameRunning = false;
            isAppRunning = false;
        } else if (!isGameRunning && input.startsWith("move")) {
            System.out.println("No game is running currently. \n");
        } else if (input.equals("commands")) {
            System.out.println("Available commands: (start, scores, exit, saveandexit, load, commands, move ur/ul/dr/dl) \n");
        } else if (input.equals("scores")) {
            printHighScores();
        } else if (input.equals("saveandexit")) {
            if (!isGameRunning) {
                System.out.println("No Game Is Running.");
            } else {
                saveGameState();
            }
            isGameRunning = false;
            isAppRunning = false;

        } else if (input.equals("load")) {
            if (loadGameState()) {
                isGameRunning = true;
                mapPrinter.printMap(gameStateService.getMap());
            } else {
                System.out.println("No save found.");
            }
        }
    }

    private void saveWinToDb() throws SQLException {
        scoreRepository.increaseScore(userName);
    }

    private void printHighScores() {
        String[][] highScores = scoreRepository.getTopFiveHighScores();
        System.out.println("\nHigh Scores:");
        for (String[] score : highScores) {
            if (score[0] != null) {
                System.out.println(score[0] + ": " + score[1]);
            }
        }
        System.out.println("");
    }

    private void saveGameState() {
        gameStateRepository.saveGameState(userName, gameStateService);
    }

    private boolean loadGameState() {
        GameStateService gameState = gameStateRepository.getGameState(userName);

        if (gameState != null) {
            gameStateService = gameState;
            return true;
        }
        return false;
    }

    private void deleteGameStateIfExists() {
        gameStateRepository.deleteGameStateIfExists(userName);
    }
}
