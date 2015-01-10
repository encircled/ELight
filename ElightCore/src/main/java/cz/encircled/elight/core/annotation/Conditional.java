package cz.encircled.elight.core.annotation;

import cz.encircled.elight.core.ComponentCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated component will be included to application context only if it's {@link ComponentCondition} returns true.
 * @see ComponentCondition
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Conditional {

    Class<? extends ComponentCondition> value();

}
