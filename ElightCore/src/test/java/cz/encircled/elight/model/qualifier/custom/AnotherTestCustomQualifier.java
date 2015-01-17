package cz.encircled.elight.model.qualifier.custom;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kisel on 1/12/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Qualifier
public @interface AnotherTestCustomQualifier {

    AnotherColor anotherColor();

    public enum AnotherColor {
        WHITE, BLACK;
    }

}
