package cz.encircled.elight.core.exception;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class AmbiguousDependencyException extends RuntimeELightException {

    public AmbiguousDependencyException(Class<?> clazz) {
        super("Ambiguous dependency for class: " + clazz.getName() + ". You can use qualifiers to specify concrete component.");
    }

}
