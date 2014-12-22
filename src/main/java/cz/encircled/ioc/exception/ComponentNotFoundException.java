package cz.encircled.ioc.exception;

import cz.encircled.ioc.util.CollectionUtil;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ComponentNotFoundException extends RuntimeELightException {

    public ComponentNotFoundException(String name) {
        super("Component not found for name " + name);
    }

    public ComponentNotFoundException(Class<?> clazz) {
        super("Component not found for class " + clazz.getName());
    }

    public ComponentNotFoundException(Class<?>... classes) {
        super("Component not found for classes " + CollectionUtil.stringify(classes));
    }

}
