package hu.akosbalogh;

import java.util.Scanner;

public class InputController {
    public String GetUserInput(Map map) {
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
                    System.out.println(input.charAt(0));
                    input = scanner.next();
                } else {
                    if (input.charAt(1) != 'r' && input.charAt(1) != 'l') {
                        System.out.println("Incorrect move.");
                        System.out.println(input.charAt(1));
                        input = scanner.next();
                    } else {
                        if (!mapValidator.isSpecifiedSpaceAvailable(map,mapValidator.getWolfRowIndex(map), mapValidator.getWolfColumnIndex(map), input)) {

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
