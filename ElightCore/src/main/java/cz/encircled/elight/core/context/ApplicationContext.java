package cz.encircled.elight.core.context;

/**
 * Created by encircled on 9/19/14.
 */
public interface ApplicationContext {

    /**
     * Looking for component in application context by it's type
     *
     * @param clazz - type that component is assignable from
     * @param <T>
     * @return component from application context
     * @throws cz.encircled.elight.core.exception.ComponentNotFoundException - if component was not found in context
     */
    <T> T getComponent(Class<T> clazz);

    /**
     * Looking for component in application context by it's name
     *
     * @param name - component's name in context
     * @return component from application context
     * @throws cz.encircled.elight.core.exception.ComponentNotFoundException - if component was not found in context
     */
    Object getComponent(String name);

    /**
     * Check if component exists in context by it's name
     *
     * @param name - component's name in context
     * @return true - if component is presented in context, otherwise - false
     */
    boolean containsComponent(String name);

    /**
     * Check if component exists in context by it's type
     *
     * @param clazz - type, that component is assignable from
     * @return true - if component is presented in context, otherwise - false
     */
    boolean containsComponent(Class<?> clazz);

    /**
     * Add instance to application context. Added object can be wired to context components (if it was added before component creation)
     * or obtained via {@link #getComponent(String)} and {@link #getComponent(Class)} methods.
     * <p>
     *     Added instance wont be managed by ELight (including wiring, init and destroy methods).
     * </p>
     * @param component - instance to add
     * @param name      - (optional) name for component
     */
    void addResolvedDependency(Object component, String name);

    /**
     * Add instance to application context. Added object can be wired to context components (if it was added before component creation)
     * or obtained via {@link #getComponent(String)} and {@link #getComponent(Class)} methods.
     * <p>
     *     Added instance wont be managed by ELight (including wiring, init and destroy methods).
     * </p>
     * @param component - instance to add
     */
    void addResolvedDependency(Object component);

    /**
     * Start context initializing
     * @return {@link cz.encircled.elight.core.context.ApplicationContext} itself
     */
    ApplicationContext initialize();

    /**
     * Destroys application context and calls destroy method on components, that are in singleton scope
     */
    void destroy();

}
