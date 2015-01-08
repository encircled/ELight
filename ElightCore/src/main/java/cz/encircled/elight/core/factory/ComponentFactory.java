package cz.encircled.elight.core.factory;

import cz.encircled.elight.core.ComponentDefinition;
import cz.encircled.elight.core.ComponentPostProcessor;

import java.util.List;

/**
 * Created by Encircled on 22-Dec-14.
 */
public interface ComponentFactory {

    void registerDefinition(ComponentDefinition componentDefinition);

    void registerPostProcessor(Class<? extends ComponentPostProcessor> componentPostProcessor);

    Object getComponent(String name);

    <T> T getComponent(Class<T> type);

    <T> List<T> getComponents(Class<T> type);

    boolean containsType(Class<?> clazz);

    void addResolvedDependency(Object component, String name);

    void instantiateSingletons();

}
