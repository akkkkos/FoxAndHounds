package hu.akosbalogh;

import java.util.Scanner;

/**
 * Todo.
 */
public class InputController {

    /**
     * Todo.
     *
     * @return Todo.
     */
    public int getUserInputBeforeGame() {
        // TODO Ask for map size and others.
        return 8;
    }

    /**
     * Todo.
     *
     * @param map Todo.
     * @return Todo.
     */
    public String getUserInputForGame(Map map) {
        MapValidator mapValidator = new MapValidator();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        boolean validInput = false;


        while (!validInput) {
            if (input.length() != 2) {
                System.out.println("Incorrect format.");
                input = scanner.next();
            } else {
                if (input.charAt(0) != 'u' && input.charAt(0) != 'd') {
                    System.out.println("Incorrect move.");
                    input = scanner.next();
                } else {
                    if (input.charAt(1) != 'r' && input.charAt(1) != 'l') {
                        System.out.println("Incorrect move.");
                        input = scanner.next();
                    } else {
                        if (!mapValidator.isSpecifiedSpaceAvailable(map,
                                mapValidator.searchFoxRowIndex(map),
                                mapValidator.searchFoxColumnIndex(map),
                                input)) {

                            System.out.println("Space Unavailable for move.");
                            input = scanner.next();
                        } else {
                            validInput = true;
                        }
                    }
                }
            }
        }
        return input;
    }
}
