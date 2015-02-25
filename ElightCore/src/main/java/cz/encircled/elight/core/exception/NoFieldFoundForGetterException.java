package cz.encircled.elight.core.exception;

import java.lang.reflect.Method;

/**
 * Created by Kisel on 2/25/2015.
 */
public class NoFieldFoundForGetterException extends RuntimeELightException {

    public NoFieldFoundForGetterException(Method method) {
        super("No field found for getter method " + method);
    }

}
