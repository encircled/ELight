package cz.encircled.ioc.core;

/**
 * Created by Encircled on 22-Dec-14.
 */
public interface ComponentCondition {

    boolean addToContext(Class<?> clazz);

}
