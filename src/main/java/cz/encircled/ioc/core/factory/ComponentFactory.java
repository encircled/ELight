package cz.encircled.ioc.core.factory;

import cz.encircled.ioc.component.ComponentDefinition;

import java.util.List;

/**
 * Created by Encircled on 22-Dec-14.
 */
public interface ComponentFactory {

    void registerDefinition(ComponentDefinition componentDefinition);

    Object getComponent(String name);

    <T> T getComponent(Class<T> type);

    <T> List<T> getComponents(Class<T> type);

    boolean containsType(Class<?> clazz);

    void addComponent(String name);

    void instantiateSingletons();

}
