package cz.encircled.elight.core.creator;

/**
 * Created by Encircled on 16/09/2014.
 */

/**
 * Implementation is responsible for creating a new instance of specified component class
 */
public interface InstanceCreator {

    /**
     * Create new instance
     * @param clazz - component <code>clazz</code> to create
     * @param <T>
     * @return new instance of <code>clazz</code>
     */
    <T> T createInstance(Class<T> clazz);

}
