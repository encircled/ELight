package cz.encircled.elight.core.exception;

import java.util.Arrays;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class AmbiguousDependencyException extends RuntimeELightException {

    public AmbiguousDependencyException(Class<?> clazz) {
        super("Ambiguous dependency for class: " + clazz.getName() + ". You can use qualifiers to specify concrete component.");
    }

    public AmbiguousDependencyException(Class<?> clazz, Object[] qualifiers) {
        super("Ambiguous dependency for class: " + clazz.getName() + " and qualifiers " + Arrays.toString(qualifiers) +
                ". You can use qualifiers to specify concrete component.");
    }

}
