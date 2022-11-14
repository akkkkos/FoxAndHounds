package hu.akosbalogh.input;

import java.util.Scanner;

import org.springframework.stereotype.Service;

/**
 * Input controller for Fox and Hounds Game.
 */
@Service
public class InputController {
    private final Scanner scanner;

    public InputController() {
        scanner = new Scanner(System.in);
    }

    /**
     * Checks if the input from user is in correct format.
     *
     * @return Returns true if the input is in correct format.
     */
    public boolean isUserMoveCorrectFormat(String input) {
        if (input.length() != 7) {
            System.out.println("Incorrect format.");
            return false;
        } else {
            String move = input.substring(5);
            if (move.charAt(0) != 'u' && move.charAt(0) != 'd') {
                System.out.println("Incorrect move.");
                return false;
            } else {
                if (move.charAt(1) != 'r' && move.charAt(1) != 'l') {
                    System.out.println("Incorrect move.");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets input from the user.
     *
     * @return Returns the command if the input is a known command . If it's not then it returns unknown.
     */
    public String getUserInput() {
        String input = scanner.nextLine();

        if (input.equals("exit")) {
            return "exit";
        } else if (input.equals("start")) {
            return "start";
        } else if (input.startsWith("move")) {
            return input;
        } else if (input.equals("commands")) {
            return "commands";
        } else if (input.equals("scores")) {
            return "scores";
        }

        return "unknown";
    }

    /**
     * Gets the map size from the commandline from the user. If it's incorrect it asks again until entered value is valid.
     *
     * @return Returns map size given by user (4/6/8/10/12).
     */
    public int getMapSizeFromUser() {
        int mapSize;
        String input;

        System.out.print("\nEnter map size (4/6/8/10/12): ");
        input = scanner.nextLine();
        mapSize = Integer.parseInt(input);

        while (!validMapSize(mapSize)) {
            System.out.println("Incorrect map size!");
            System.out.print("\nEnter map size (4/6/8/10/12): ");
            input = scanner.nextLine();
            mapSize = Integer.parseInt(input);
        }

        return mapSize;
    }

    /**
     * Asks for the users name.
     *
     * @return Returns the answer.
     */
    public String getUserNameFromUser() {
        return scanner.nextLine();
    }

    private boolean validMapSize(int n) {
        if (n < 4 || n > 12) {
            return false;
        }
        if (n % 2 != 0) {
            return false;
        }
        return true;
    }
}
