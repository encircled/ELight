package cz.encircled.elight.core.definition;

import cz.encircled.elight.core.definition.dependency.DependencyDescription;
import cz.encircled.elight.core.creator.InstanceCreator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Encircled on 16/09/2014.
 */
public class ComponentDefinition {

    public static final String PROTOTYPE = "prototype";

    public static final String SINGLETON = "singleton";

    public String name;

    public Object[] qualifiers;

    public Class<?> clazz;

    public boolean isSingleton;

    public boolean isWireAllowed;

    public int order;

    public List<DependencyDescription> dependencies;

    public Class<? extends InstanceCreator> instanceCreator;

    public Method initMethod;

    public Method destroyMethod;

    public ComponentDefinition(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean hasInstanceCreator() {
        return instanceCreator != null;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    @Override
    public String toString() {
        return "ComponentDefinition{" +
                "name='" + name + '\'' +
                ", qualifiers=" + qualifiers +
                ", clazz=" + clazz +
                ", isSingleton=" + isSingleton +
                ", isWireAllowed=" + isWireAllowed +
                ", order=" + order +
                ", dependencies=" + dependencies +
                ", instanceCreator=" + instanceCreator +
                ", initMethod=" + initMethod +
                ", destroyMethod=" + destroyMethod +
                '}';
    }
}
