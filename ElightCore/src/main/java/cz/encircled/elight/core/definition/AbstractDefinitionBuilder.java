package cz.encircled.elight.core.definition;

import cz.encircled.elight.core.ComponentDefinition;
import cz.encircled.elight.core.DependencyDescription;
import cz.encircled.elight.core.creator.InstanceCreator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by encircled on 9/19/14.
 */
public abstract class AbstractDefinitionBuilder implements DefinitionBuilder {

    public AbstractDefinitionBuilder() {
    }

    public ComponentDefinition buildDefinition(Class<?> clazz) {
        ComponentDefinition definition = new ComponentDefinition(clazz);


        String scope = getScope(clazz);
        definition.isSingleton = scope == null || scope.equals(ComponentDefinition.SINGLETON);

        definition.name = getName(clazz);
        definition.qualifier = getQualifier(clazz);
        definition.initMethod = getInitMethod(clazz);
        definition.destroyMethod = getDestroyMethod(clazz);
        definition.dependencies = getDependencyDescriptions(definition.clazz);
        definition.order = getOrder(definition.clazz);
        definition.instanceCreator = getInstanceCreator(clazz);

        return definition;
    }

    protected abstract Object getQualifier(Class<?> clazz);

    protected abstract int getOrder(Class<?> clazz);

    protected abstract Method getDestroyMethod(Class<?> clazz);

    protected abstract Method getInitMethod(Class<?> clazz);

    protected abstract List<DependencyDescription> getDependencyDescriptions(Class<?> clazz);

    protected abstract String getScope(Class<?> clazz);

    protected abstract Class<? extends InstanceCreator> getInstanceCreator(Class<?> clazz);

}
