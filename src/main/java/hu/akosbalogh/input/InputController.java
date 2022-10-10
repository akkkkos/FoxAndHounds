package hu.akosbalogh.input;

import java.util.Scanner;

/**
 * Todo.
 */
public class InputController {
    private final Scanner scanner;

    public InputController() {
        scanner = new Scanner(System.in);
    }

    /**
     * Todo.
     *
     * @return Todo.
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
     * Todo.
     *
     * @return Todo.
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
        }

        return "unknown";
    }

    /**
     * Todo.
     *
     * @return Todo.
     */
    public int getMapSizeFromUser() {
        int mapSize;
        String input;

        System.out.print("\nEnter map size (6/8/10/12): ");
        input = scanner.nextLine();
        mapSize = Integer.parseInt(input);

        while (!validMapSize(mapSize)) {
            System.out.println("Incorrect map size!");
            System.out.print("\nEnter map size (6/8/10/12): ");
            input = scanner.nextLine();
            mapSize = Integer.parseInt(input);
        }

        return mapSize;
    }

    /**
     * Todo.
     *
     * @return Todo.
     */
    public String getUserNameFromUser() {
        return scanner.nextLine();
    }

    private boolean validMapSize(int n) {
        if (n < 6 || n > 12) {
            return false;
        }
        if (n % 2 != 0) {
            return false;
        }
        return true;
    }
}
