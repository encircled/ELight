package cz.encircled.elight.core;

/**
 * Implementations of this interface are supposed to configure components before putting to context.
 * Multiple implementations are allowed.
 * <p>
 *     {@link #preProcess(Object)} is called just after new component instance is created,
 *     before dependency injection and calling initialize method
 * </p>
 * <p>
 *     {@link #postProcess(Object)} is called after dependency injection and calling initialize method,
 *     just before putting component to context
 * </p>
 */
public interface ComponentPostProcessor {

    Object preProcess(Object component);

    Object postProcess(Object component);

}
