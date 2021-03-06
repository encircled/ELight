package cz.encircled.elight.core.factory;

import cz.encircled.elight.core.Caching;
import cz.encircled.elight.core.definition.ComponentDefinition;
import cz.encircled.elight.core.ComponentPostProcessor;

import java.util.List;

/**
 * Created by Encircled on 22-Dec-14.
 */
public interface ComponentFactory extends Caching {

    void registerDefinition(ComponentDefinition componentDefinition);

    void registerPostProcessor(Class<? extends ComponentPostProcessor> componentPostProcessor);

    Object getComponent(String name);

    Object getComponent(String name, boolean required);

    <T> T getComponentOfType(Class<T> type);

    <T> T getComponentOfType(Class<T> type, boolean required);

    <T> List<T> getComponentsOfType(Class<T> type);

    boolean containsComponent(Class<?> clazz);

    boolean containsComponent(String name);

    void addResolvedDependency(Object component, String name);

    void instantiateSingletons();

    void onDestroy();
}
