package hu.akosbalogh.game;

import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.input.InputService;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.MapService;
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
    private final MapService mapService;
    private final InputService inputService;
    private final MapValidator mapValidator;
    private final MapPrinter mapPrinter;
    private final ScoreRepository scoreRepository;
    private String userName;

    @Autowired
    public GameService(MapService mapService,
                       InputService inputService,
                       MapValidator mapValidator,
                       MapPrinter mapPrinter,
                       ScoreRepository scoreRepository) {
        this.mapService = mapService;
        this.inputService = inputService;
        this.mapValidator = mapValidator;
        this.mapPrinter = mapPrinter;
        this.scoreRepository = scoreRepository;
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
                            boolean isMoveValid = mapValidator.isSpecifiedSpaceAvailable(mapService.getMap(),
                                    mapService.getFoxPosition()[0],
                                    mapService.getFoxPosition()[1],
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
        mapService.moveFox(move);

        if (mapValidator.isFoxWinner(mapService.getMap())) {
            System.out.println("\n");
            mapPrinter.printMap(mapService.getMap());
            System.out.println("Win!");
            saveWinToDb();
            isGameRunning = false;
        } else {

            mapService.moveRandomHound();
            System.out.println("\nMoving Random Hound...");

            if (mapValidator.isHoundWinner(mapService.getMap())) {
                System.out.println("\n");
                mapPrinter.printMap(mapService.getMap());
                System.out.println("Lose!");
                isGameRunning = false;
            }
        }
        mapPrinter.printMap(mapService.getMap());
    }

    private void performKnownCommand(String input) throws Exception {
        if (input.equals("start")) {
            isGameRunning = true;
            int mapSize = inputService.getMapSizeFromUser();
            mapService.buildMap(mapSize);
            System.out.println("\n\nStarting game...");
            mapPrinter.printMap(mapService.getMap());
        } else if (input.equals("exit")) {
            isGameRunning = false;
            isAppRunning = false;
        } else if (!isGameRunning && input.startsWith("move")) {
            System.out.println("No game is running currently. \n");
        } else if (input.equals("commands")) {
            System.out.println("Available commands: (start, scores, exit, commands, move ur/ul/dr/dl) \n");
        } else if (input.equals("scores")) {
            printHighScores();
        }
    }

    private void saveWinToDb() {
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

}
