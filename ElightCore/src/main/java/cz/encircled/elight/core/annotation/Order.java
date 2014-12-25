package cz.encircled.elight.core.annotation;

import cz.encircled.elight.core.context.ContextConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    int value() default ContextConstants.DEFAULT_ORDER;

}
