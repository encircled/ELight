package cz.encircled.elight.core;

/**
 * Created by Encircled on 22-Dec-14.
 */

/**
 * Implementation decides if some class will be included to context, or not.
 * <p>
 *     {@link ComponentCondition} itself wont be managed by context (if it is not explicitly specified).
 *     New instance of {@link ComponentCondition} will be created for each conditional component.
 * </p>
 * @see cz.encircled.elight.core.annotation.Conditional
 */
public interface ComponentCondition {

    /**
     * @param clazz candidate class
     * @return true - if <code>clazz</code> have to be included to context, otherwise - false
     */
    boolean addToContext(Class<?> clazz);

}
