package cz.encircled.elight.core.exception;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class WiredMapGenericException extends RuntimeELightException {

    public WiredMapGenericException() {
        super("For wiring a map, it's key or value must be component from context");
    }

}
