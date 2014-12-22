package cz.encircled.ioc.core.context;

import org.jetbrains.annotations.Nullable;

/**
 * Created by encircled on 9/19/14.
 */
public interface Context {

    <T> T getComponent(Class<T> clazz);

    @Nullable
    Object getComponent(String name);

    void addComponent(Object component);

}
