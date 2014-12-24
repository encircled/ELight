package cz.encircled.ioc.core;

import cz.encircled.ioc.core.creator.InstanceCreator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Encircled on 16/09/2014.
 */
public class ComponentDefinition {

    public static final String PROTOTYPE = "prototype";

    public static final String SINGLETON = "singleton";

    public String name;

    public Class<?> clazz;

    public boolean isSingleton;

    public boolean isWireAllowed;

    public int order;

    public List<DependencyDescription> dependencies;

    public Class<? extends InstanceCreator> componentInstanceCreator;

    public Method initMethod;

    public String destroyMethodName;

    public ComponentDefinition(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean hasFactory() {
        return componentInstanceCreator != null;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    @Override
    public String toString() {
        return "ComponentDefinition{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
