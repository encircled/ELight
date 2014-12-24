package cz.encircled.elight.core.exception;

/**
 * Created by Encircled on 20-Dec-14.
 */
public class ELightException extends Exception {

    public ELightException() {

    }

    public ELightException(String message) {
        super(message);
    }

    public ELightException(Throwable cause) {
        super(cause);
    }

}
