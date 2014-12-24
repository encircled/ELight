package cz.encircled.ioc.annotation;

import cz.encircled.ioc.core.ComponentCondition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Conditional {

    Class<? extends ComponentCondition> value();

}
