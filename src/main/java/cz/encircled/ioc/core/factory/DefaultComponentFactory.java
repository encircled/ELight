package cz.encircled.ioc.core.factory;

import cz.encircled.ioc.component.ComponentDefinition;
import cz.encircled.ioc.component.DependencyDescription;
import cz.encircled.ioc.exception.ComponentNotFoundException;
import cz.encircled.ioc.exception.WiredMapGenericException;
import cz.encircled.ioc.util.CollectionUtil;
import cz.encircled.ioc.util.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class DefaultComponentFactory implements ComponentFactory {

    private static final Logger log = LogManager.getLogger();

    private Map<String, ComponentDefinition> definitions = new ConcurrentHashMap<>(32);

    private Map<Class<?>, String[]> typeToName = new ConcurrentHashMap<>(32);

    private Map<String, Object> singletonInstances = new HashMap<>(32);

    @Override
    public void instantiateSingletons() {
        definitions.values().stream().forEach(definition -> {
            if (definition.isSingleton()) {
                Object instance = ReflectionUtil.instance(definition.clazz);

                singletonInstances.put(definition.name, instance);
            }
        });
        singletonInstances.forEach((name, instance) -> {
            ComponentDefinition definition = getDefinition(name);
            definition.dependencies.forEach(dependency -> {
                resolveDependency(name, instance, dependency);
            });
        });
        singletonInstances.forEach((name, instance) -> {
            ComponentDefinition definition = getDefinition(name);
            if (definition.initMethod != null) {
                ReflectionUtil.invokeMethod(definition.initMethod, instance);
            }
        });
    }

    public void onContextInitializedFinish() {
        //componentDefinition.superClasses.stream().forEach(c -> typeToName.put(c, componentDefinition.name));
    }

    @Override
    public void registerDefinition(ComponentDefinition componentDefinition) {
        // TODO exception
        log.debug("Register new definition for name {}", componentDefinition.name);
        definitions.put(componentDefinition.name, componentDefinition);
    }

    @Override
    public Object getComponent(String name) {
        // TODO prototype
        Object instance = singletonInstances.get(name);
        if (instance == null) {
            throw new ComponentNotFoundException(name);
        }
        return instance;
    }

    public ComponentDefinition getDefinition(String name) {
        // TODO exception
        return definitions.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        // TODO exception
        for (ComponentDefinition definition : definitions.values()) {
            if (type.isAssignableFrom(definition.clazz)) {
                return (T) singletonInstances.get(definition.name);
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NotNull
    public <T> List<T> getComponents(Class<T> type) {
        List<T> components = new ArrayList<>();
        for (ComponentDefinition definition : definitions.values()) {
            if (type.isAssignableFrom(definition.clazz)) {
                components.add((T) singletonInstances.get(definition.name));
            }
        }
        return components;
    }

    @Override
    public boolean containsType(Class<?> clazz) {
        for (ComponentDefinition definition : definitions.values()) {
            if (clazz.isAssignableFrom(definition.clazz)) {
                return true;
            }
        }
        return false;
    }

    public List<ComponentDefinition> getDefinitions(Class<?> type) {
        return definitions.values().stream().filter(definition -> type.isAssignableFrom(definition.clazz)).collect(Collectors.toList());
    }

    @Override
    public void addComponent(String name) {
        // TODO
    }

    @SuppressWarnings("unchecked")
    private void resolveDependency(String name, Object instance, DependencyDescription dependency) {
        log.debug("Resolve dependency: field {} in bean {}", dependency.targetField.getName(), name);
        // TODO map, collections, exceptions, required
        Class<?> type = dependency.targetField.getType();
        Object objToInject;
        if (Collection.class.isAssignableFrom(type)) {
            Class<?> genericClass = ReflectionUtil.getGenericClasses(dependency.targetField)[0];
            Collection<Object> appropriateCollection = CollectionUtil.getAppropriateCollection((Class<? extends Collection<Object>>) type);

            List<ComponentDefinition> definitionsByType = getDefinitions(genericClass);
            List<Object> componentsForCollection = new ArrayList<>(definitionsByType.size());
            Collections.sort(definitionsByType, (o1, o2) -> Integer.compare(o1.order, o2.order));
            for (ComponentDefinition componentDefinition : definitionsByType) {
                // TODO prototype scope
                componentsForCollection.add(singletonInstances.get(componentDefinition.name));
            }

            appropriateCollection.addAll(componentsForCollection);
            objToInject = appropriateCollection;
        } else if (type.isArray()) {

            List<?> componentsToInject = getComponents(type.getComponentType());
            objToInject = CollectionUtil.collectionToArray(componentsToInject, type.getComponentType());

        } else if (Map.class.isAssignableFrom(type)) {
            Class[] genericClasses = ReflectionUtil.getGenericClasses(dependency.targetField);
            Class<Object> keyGenericClass = genericClasses[0];
            Class<Object> valueGenericClass = genericClasses[1];

            List<Object> componentsForKey = getComponents(keyGenericClass);
            List<Object> componentsForValue = getComponents(valueGenericClass);
            Map<Object, Object> appropriateMap = CollectionUtil.getAppropriateMap((Class<? extends Map<Object, Object>>) type);
            if (componentsForKey.isEmpty() && !componentsForValue.isEmpty()) {
                objToInject = CollectionUtil.collectionToMap(getComponents(valueGenericClass), appropriateMap);
            } else if (!componentsForKey.isEmpty() && componentsForValue.isEmpty()) {
                objToInject = CollectionUtil.collectionToMap(getComponents(keyGenericClass), appropriateMap);
            } else {
                throw new WiredMapGenericException();
            }
        } else {
            objToInject = getComponent(type);
        }
        ReflectionUtil.setField(instance, dependency.targetField, objToInject);
    }

}
