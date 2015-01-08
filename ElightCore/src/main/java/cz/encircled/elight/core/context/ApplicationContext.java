package cz.encircled.elight.core.context;

import org.jetbrains.annotations.Nullable;

/**
 * Created by encircled on 9/19/14.
 */
public interface ApplicationContext {

    <T> T getComponent(Class<T> clazz);

    @Nullable
    Object getComponent(String name);

    boolean containsComponent(String name);

    boolean containsComponent(Class<?> clazz);

    void addResolvedDependency(Object component, String name);

    void addResolvedDependency(Object component);

    ApplicationContext initialize();

}
