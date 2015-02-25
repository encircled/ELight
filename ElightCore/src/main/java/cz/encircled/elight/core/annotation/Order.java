package cz.encircled.elight.core.annotation;

import cz.encircled.elight.core.definition.DefinitionBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Order value is used for sorting, when wiring a list or array
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    int value() default DefinitionBuilder.DEFAULT_COMPONENT_ORDER;

}
