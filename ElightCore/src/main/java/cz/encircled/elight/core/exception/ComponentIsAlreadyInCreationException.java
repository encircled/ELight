package cz.encircled.elight.core.exception;

/**
 * Created by Kisel on 1/17/2015.
 */
public class ComponentIsAlreadyInCreationException extends RuntimeELightException {

    public ComponentIsAlreadyInCreationException(String name) {
        super("Component with name " + name + " is already in creation. Self reference probably.");
    }

}
