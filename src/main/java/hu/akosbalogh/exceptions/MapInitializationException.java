package hu.akosbalogh.exceptions;

/**
 * Exception for the map not being initialized yet.
 */
public class MapInitializationException extends Exception {
    public MapInitializationException() {
        super("Map hasn't been initialized");
    }
}
