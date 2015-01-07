package cz.encircled.elight.core.annotation;

import cz.encircled.elight.core.creator.InstanceCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Work on 1/7/2015.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Creator {

    Class<? extends InstanceCreator> value();

}
