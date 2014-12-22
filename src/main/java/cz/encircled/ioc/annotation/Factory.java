package cz.encircled.ioc.annotation;

import cz.encircled.ioc.component.creator.InstanceCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Encircled on 19/09/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Factory {

    Class<? extends InstanceCreator> value();

}
