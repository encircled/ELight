package cz.encircled.elight.core.factory;

import cz.encircled.elight.core.ComponentDefinition;
import cz.encircled.elight.core.ComponentPostProcessor;
import cz.encircled.elight.core.DependencyDescription;
import cz.encircled.elight.core.exception.*;
import cz.encircled.elight.core.util.CollectionUtil;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private Map<String, Object> resolvedDependencies = new HashMap<>(32);

    private List<ComponentPostProcessor> componentPostProcessors = new ArrayList<>();

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
        for(ComponentDefinition definition : definitions.values()) {
            if(definition.isSingleton && definition.destroyMethod != null) {
                ReflectionUtil.invokeMethod(definition.destroyMethod, getComponent(definition.name));
            }
        }
    }

    private void afterComponentInstantiation(Object instance, ComponentDefinition definition) {
        if (definition.initMethod != null) {
            ReflectionUtil.invokeMethod(definition.initMethod, instance);
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
        if (definition.isSingleton()) {
            return singletonInstances.get(name);
        } else {
            return getInitializedPrototypeComponent(definition);
        }
    }

    @Override
    public Object getComponent(String name, boolean required) {
        if (required) {
            return getComponent(name);
        } else {
            return containsComponent(name) ? getComponent(name) : null;
        }
    }

    private Object getInitializedPrototypeComponent(ComponentDefinition definition) {
        Object instance = instantiateComponent(definition);
        definition.dependencies.forEach(dependency -> {
            resolveDependency(definition.name, instance, dependency);
        });
        afterComponentInstantiation(instance, definition);
        return instance;
    }

    public ComponentDefinition getDefinition(String name) {
        // TODO exception
        return definitions.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        List<T> components = getComponents(type);
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
    public <T> T getComponent(Class<T> type, boolean required) {
        if(required) {
            return getComponent(type);
        } else {
            return containsComponent(type) ? getComponent(type) : null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getComponents(Class<T> type) {
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

    public List<ComponentDefinition> getDefinitions(Class<?> type) {
        return definitions.values().stream().filter(definition -> type.isAssignableFrom(definition.clazz)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private void resolveDependency(String name, Object instance, DependencyDescription dependency) {
        log.debug("Resolve dependency: field {} in component with name {}", dependency.targetField.getName(), name);
        // TODO map, collections, exceptions, required
        Class<?> type = dependency.targetField.getType();
        Object objToInject = null;
        if (Collection.class.isAssignableFrom(type)) {
            Class<Object> genericClass = ReflectionUtil.getGenericClasses(dependency.targetField)[0];
            Collection<Object> appropriateCollection = CollectionUtil.getAppropriateCollection((Class<? extends Collection<Object>>) type);

            List<ComponentDefinition> definitionsByType = getDefinitions(genericClass);
            List<Object> componentsForCollection = new ArrayList<>(definitionsByType.size());
            Collections.sort(definitionsByType, (o1, o2) -> Integer.compare(o1.order, o2.order));
            for (ComponentDefinition componentDefinition : definitionsByType) {
                componentsForCollection.add(getComponent(componentDefinition.name));
            }
            appropriateCollection.addAll(componentsForCollection);
            for (Object candidate : resolvedDependencies.values()) {
                if (genericClass.isAssignableFrom(candidate.getClass())) {
                    appropriateCollection.add(candidate);
                }
            }
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
                if(dependency.isRequired)
                    throw new WiredMapGenericException();
                else
                    objToInject = appropriateMap;
            }
        } else {
            if (dependency.hasNameQualifier()) {
                objToInject = getComponent(dependency.nameQualifier, dependency.isRequired);
            } else if (dependency.qualifiers != null) {
                objToInject = getComponentByQualifier(type, dependency.qualifiers, dependency.isRequired);
            } else {
                objToInject = getComponent(type, dependency.isRequired);
            }
        }
        ReflectionUtil.setField(instance, dependency.targetField, objToInject);
    }

    private Object getComponentByQualifier(Class<?> type, Object[] qualifiers, boolean isRequired) {
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

}
