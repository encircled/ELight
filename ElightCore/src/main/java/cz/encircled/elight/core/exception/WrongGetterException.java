package cz.encircled.elight.core.exception;

import java.lang.reflect.Method;

/**
 * Created by Kisel on 1/17/2015.
 */
public class WrongGetterException extends RuntimeELightException {

    public WrongGetterException(Method method) {
        super("Method " + method + " is not correct getter method");
    }

}
