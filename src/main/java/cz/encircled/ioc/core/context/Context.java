package cz.encircled.ioc.core.context;

import org.jetbrains.annotations.Nullable;

/**
 * Created by encircled on 9/19/14.
 */
public interface Context {

    <T> T getComponent(Class<T> clazz);

    @Nullable
    Object getComponent(String name);

    boolean containsComponent(String name);

    boolean containsComponent(Class<?> clazz);

    void addComponent(Object component);

}
