package cz.encircled.elight.core.factory;

import cz.encircled.elight.core.definition.ComponentDefinition;
import cz.encircled.elight.core.ComponentPostProcessor;
import cz.encircled.elight.core.definition.dependency.DependencyDescription;
import cz.encircled.elight.core.definition.dependency.DependencyInjectionType;
import cz.encircled.elight.core.exception.*;
import cz.encircled.elight.core.util.CollectionUtil;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Provider;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class DefaultComponentFactory implements ComponentFactory {

    private static final Logger log = LogManager.getLogger();

    private Map<String, ComponentDefinition> definitions = new ConcurrentHashMap<>(32);

    // TODO cache
    private Map<Class<?>, String[]> typeToName = new ConcurrentHashMap<>(32);

    private Map<String, Object> singletonInstances = new HashMap<>(32);

    private Map<String, Object> resolvedDependencies = new HashMap<>(32);

    private List<ComponentPostProcessor> componentPostProcessors = new ArrayList<>();

    private Set<String> componentsInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void instantiateSingletons() {
        definitions.values().stream().forEach(definition -> {
            if (definition.isSingleton()) {
                Object instance = instantiateComponent(definition);
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
            afterComponentInstantiation(instance, definition);
        });
    }

    @Override
    public void onDestroy() {
        log.debug("Call singletons PreDestroy methods");
        for (ComponentDefinition definition : definitions.values()) {
            if (definition.isSingleton && definition.destroyMethod != null) {
                ReflectionUtil.invokeMethod(getComponent(definition.name), definition.destroyMethod);
            }
        }
    }

    @Override
    public void registerDefinition(ComponentDefinition componentDefinition) {
        String name = componentDefinition.name;
        log.debug("Register new definition for name {} : {}", name, componentDefinition);
        if (definitions.containsKey(name) || resolvedDependencies.containsKey(name)) {
            throw new DuplicatedComponentException(name);
        }
        definitions.put(name, componentDefinition);
    }

    @Override
    public void registerPostProcessor(Class<? extends ComponentPostProcessor> componentPostProcessor) {
        log.debug("Register new post processor {}" + componentPostProcessor);
        componentPostProcessors.add(ReflectionUtil.instance(componentPostProcessor));
    }

    @Override
    public Object getComponent(String name) {
        Object resolvedComponent = resolvedDependencies.get(name);
        if (resolvedComponent != null) {
            return resolvedComponent;
        }
        ComponentDefinition definition = definitions.get(name);
        if (definition == null) {
            throw new ComponentNotFoundException(name);
        }
        if (!componentsInCreation.add(name)) {
            throw new ComponentIsAlreadyInCreationException(name);
        }
        Object component = definition.isSingleton ? singletonInstances.get(name) : getInitializedPrototypeComponent(definition);
        componentsInCreation.remove(name);
        return component;
    }

    @Override
    public Object getComponent(String name, boolean required) {
        if (required) {
            return getComponent(name);
        } else {
            return containsComponent(name) ? getComponent(name) : null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getComponentOfType(Class<T> type) {
        List<T> components = getComponentsOfType(type);
        switch (components.size()) {
            case 0:
                throw new ComponentNotFoundException(type);
            case 1:
                return components.get(0);
            default:
                throw new AmbiguousDependencyException(type);
        }
    }

    @Override
    public <T> T getComponentOfType(Class<T> type, boolean required) {
        if (required) {
            return getComponentOfType(type);
        } else {
            return containsComponent(type) ? getComponentOfType(type) : null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getComponentsOfType(Class<T> type) {
        List<T> components = new ArrayList<>();
        for (Object candidate : resolvedDependencies.values()) {
            if (type.isAssignableFrom(candidate.getClass())) {
                components.add((T) candidate);
            }
        }
        for (ComponentDefinition definition : definitions.values()) {
            if (type.isAssignableFrom(definition.clazz)) {
                components.add((T) getComponent(definition.name));
            }
        }
        return components;
    }

    @Override
    public boolean containsComponent(Class<?> clazz) {
        for (Object candidate : resolvedDependencies.values()) {
            if (clazz.isAssignableFrom(candidate.getClass())) {
                return true;
            }
        }
        for (ComponentDefinition definition : definitions.values()) {
            if (clazz.isAssignableFrom(definition.clazz)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsComponent(String name) {
        return resolvedDependencies.containsKey(name) || definitions.containsKey(name);
    }

    @Override
    public void addResolvedDependency(Object component, String name) {
        log.debug("Add resolved property: name {} for object {}", name, component);
        if (StringUtils.isEmpty(name) || component == null) {
            throw new RuntimeELightException("Illegal arguments");
        }
        if (definitions.containsKey(name) || resolvedDependencies.containsKey(name)) {
            throw new DuplicatedComponentException(name);
        }
        resolvedDependencies.put(name, component);
    }

    /**
     * Resolved dependencies are not included
     */
    private List<ComponentDefinition> getDefinitionsOfType(Class<?> type) {
        return definitions.values().stream().filter(definition -> type.isAssignableFrom(definition.clazz)).collect(Collectors.toList());
    }

    private void resolveDependency(String componentName, Object componentInstance, DependencyDescription dependency) {
        log.debug("Resolve dependency: {} in component with componentName {}", dependency, componentName);

        Object objToInject = dependency.isProvider ? new DependencyProvider(dependency) : getObjectToInject(dependency);

        if (dependency.dependencyInjectionType == DependencyInjectionType.FIELD) {
            ReflectionUtil.setField(componentInstance, dependency.targetField, objToInject);
        } else {
            ReflectionUtil.invokeMethod(componentInstance, dependency.targetMethod, objToInject);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getObjectToInject(DependencyDescription dependency) {
        Object objToInject;
        Class<?> targetClass = dependency.targetClass;
        if (Collection.class.isAssignableFrom(targetClass)) {
            Class[] genericClasses = ReflectionUtil.getGenericClasses(dependency.targetType);
            if (genericClasses.length == 0) {
                throw new RawTypeException("Generic type must be specified: " + dependency.targetType);
            }
            Collection<Object> appropriateCollection = CollectionUtil.getAppropriateCollection((Class<? extends Collection<Object>>) targetClass);
            objToInject = findOrderedComponentsToInject(appropriateCollection, genericClasses[0]);
        } else if (targetClass.isArray()) {
            List<Object> componentsToInject = new ArrayList<>(2);
            Class<?> componentType = targetClass.getComponentType();
            findOrderedComponentsToInject(componentsToInject, componentType);
            objToInject = CollectionUtil.collectionToArray(componentsToInject, componentType);
        } else if (Map.class.isAssignableFrom(targetClass)) {
            Class[] genericClasses = ReflectionUtil.getGenericClasses(dependency.targetType);
            if (genericClasses.length == 0) {
                throw new RawTypeException("Generic type must be specified: " + dependency.targetType);
            }
            Class<Object> keyGenericClass = genericClasses[0];
            Class<Object> valueGenericClass = genericClasses[1];

            List<String> componentsForKey = getComponentNamesOfType(keyGenericClass);
            List<String> componentsForValue = getComponentNamesOfType(valueGenericClass);

            Map<Object, Object> appropriateMap = CollectionUtil.getAppropriateMap((Class<? extends Map<Object, Object>>) targetClass);
            if (componentsForKey.isEmpty() && !componentsForValue.isEmpty()) {
                // Map's value is managed by context
                if (keyGenericClass.equals(String.class) || keyGenericClass.equals(Object.class)) {
                    List<Object> components = getComponents(componentsForValue);
                    objToInject = CollectionUtil.collectionToMapUnsafe(componentsForValue, components, appropriateMap);
                } else {
                    throw new WiredMapGenericException("For wiring components to map as value, it's key must be a String or Object");
                }
            } else if (!componentsForKey.isEmpty() && componentsForValue.isEmpty()) {
                // Map's key is managed by context
                List<Object> components = getComponents(componentsForKey);
                objToInject = CollectionUtil.collectionToMapUnsafe(components, componentsForKey, appropriateMap);
            } else {
                if (dependency.isRequired)
                    throw new WiredMapGenericException();
                else {
                    objToInject = appropriateMap;
                }
            }
        } else {
            if (dependency.hasNameQualifier()) {
                objToInject = getComponent(dependency.nameQualifier, dependency.isRequired);
            } else if (dependency.qualifiers != null) {
                objToInject = getComponentByQualifiers(targetClass, dependency.qualifiers, dependency.isRequired);
            } else {
                objToInject = getComponentOfType(targetClass, dependency.isRequired);
            }
        }
        return objToInject;
    }

    /*
     * We must collect definitions to sort components
     */
    private Collection<Object> findOrderedComponentsToInject(Collection<Object> foundComponents, Class<?> componentType) {
        List<ComponentDefinition> definitionsByType = getDefinitionsOfType(componentType);
        Collections.sort(definitionsByType, (o1, o2) -> Integer.compare(o1.order, o2.order));
        for (ComponentDefinition componentDefinition : definitionsByType) {
            foundComponents.add(getComponent(componentDefinition.name));
        }
        for (Object candidate : resolvedDependencies.values()) {
            if (componentType.isAssignableFrom(candidate.getClass())) {
                foundComponents.add(candidate);
            }
        }
        return foundComponents;
    }

    private Object getInitializedPrototypeComponent(ComponentDefinition definition) {
        Object instance = instantiateComponent(definition);
        definition.dependencies.forEach(dependency -> {
            resolveDependency(definition.name, instance, dependency);
        });
        afterComponentInstantiation(instance, definition);
        return instance;
    }

    private ComponentDefinition getDefinition(String name) {
        ComponentDefinition definition = definitions.get(name);
        if (definition == null)
            throw new ComponentNotFoundException(name);
        return definition;
    }


    private List<String> getComponentNamesOfType(Class<?> type) {
        List<String> components = new ArrayList<>();
        resolvedDependencies.forEach((name, component) -> {
            if(type.isAssignableFrom(component.getClass())) {
                components.add(name);
            }
        });
        definitions.forEach((name, def) -> {
            if(type.isAssignableFrom(def.clazz)) {
                components.add(name);
            }
        });
        return components;
    }

    private void afterComponentInstantiation(Object instance, ComponentDefinition definition) {
        if (definition.initMethod != null) {
            ReflectionUtil.invokeMethod(instance, definition.initMethod);
        }
        for (ComponentPostProcessor processor : componentPostProcessors) {
            instance = processor.postProcess(instance);
        }
    }

    private Object instantiateComponent(ComponentDefinition definition) {
        Object instance;
        if (definition.hasInstanceCreator()) {
            log.debug("Instantiate {} with creator: {}", definition, definition.instanceCreator);
            instance = ReflectionUtil.instance(definition.instanceCreator).createInstance(definition.clazz);
        } else {
            instance = ReflectionUtil.instance(definition.clazz);
        }
        for (ComponentPostProcessor processor : componentPostProcessors) {
            instance = processor.preProcess(instance);
        }
        return instance;
    }

    private Object getComponentByQualifiers(Class<?> type, Object[] qualifiers, boolean isRequired) {
        List<ComponentDefinition> found = definitions.values().stream().unordered().filter(definition -> {
            return type.isAssignableFrom(definition.clazz) && Arrays.equals(qualifiers, definition.qualifiers);
        }).collect(Collectors.toList());
        if (found.size() == 0) {
            if (isRequired)
                throw new ComponentNotFoundException(type, qualifiers);
            return null;
        }
        if (found.size() > 1) {
            throw new AmbiguousDependencyException(type, qualifiers);
        }
        return getComponent(found.get(0).name);
    }

    private List<Object> getComponents(List<String> names) {
        if(names == null)
            throw new NullPointerException();
        return names.stream().map(this::getComponent).collect(Collectors.toList());
    }

    /**
     * Implementation of JSR 330 {@link javax.inject.Provider}
     */
    private class DependencyProvider implements Provider {

        private DependencyDescription dependencyDescription;

        public DependencyProvider(DependencyDescription dependencyDescription) {
            this.dependencyDescription = dependencyDescription;
        }

        @Override
        public Object get() {
            return getObjectToInject(dependencyDescription);
        }

    }

}
