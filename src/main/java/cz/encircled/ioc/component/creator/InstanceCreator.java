package cz.encircled.ioc.component.creator;

/**
 * Created by Encircled on 16/09/2014.
 */
public interface InstanceCreator {

    <T> T createInstance(Class<T> clazz);

}
