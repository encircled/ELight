package cz.encircled.elight.model.qualifier.custom;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Qualifier
public @interface TestCustomQualifier {

    Color color() default Color.WHITE;

    public enum Color {RED, BLACK, WHITE}
}
