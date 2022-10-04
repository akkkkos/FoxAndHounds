package hu.akosbalogh;

import static hu.akosbalogh.MapPrinter.printMap;

/**
 * The Main class.
 */
public class Main {
    public static void main(String[] args) {
        MapValidator mapValidator = new MapValidator();
        try {
            MapController map = new MapController(8);
            printMap(map.getMap());

            System.out.println(mapValidator.isSpecifiedSpaceAvailable(map.getMap(),3,0, "ul"));
            System.out.println(mapValidator.isSpecifiedSpaceAvailable(map.getMap(),3,0, "ur"));
            System.out.println(mapValidator.isSpecifiedSpaceAvailable(map.getMap(),3,0, "dl"));
            System.out.println(mapValidator.isSpecifiedSpaceAvailable(map.getMap(),3,0, "dr"));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}