package cz.encircled.elight.core.exception;

/**
 * Created by Kisel on 1/17/2015.
 */
public class SelfReferenceOnPrototypeException extends RuntimeELightException {

    public SelfReferenceOnPrototypeException() {
        // TODO
        super("Self reference on prototype scope component: ");
    }

}
