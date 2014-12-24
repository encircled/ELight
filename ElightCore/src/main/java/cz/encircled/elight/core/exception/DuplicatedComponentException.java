package cz.encircled.elight.core.exception;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class DuplicatedComponentException extends RuntimeELightException {

    public DuplicatedComponentException(String name) {
        super("Component with name " + name + " already exists in context");
    }

}
