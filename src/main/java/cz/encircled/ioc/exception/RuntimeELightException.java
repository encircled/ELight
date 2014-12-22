package cz.encircled.ioc.exception;

/**
 * Created by Encircled on 20-Dec-14.
 */
public class RuntimeELightException extends RuntimeException {

    public RuntimeELightException() {

    }

    public RuntimeELightException(String message) {
        super(message);
    }

    public RuntimeELightException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeELightException(Throwable cause) {
        super(cause);
    }

}
