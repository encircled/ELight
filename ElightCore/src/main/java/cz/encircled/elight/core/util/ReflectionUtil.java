package cz.encircled.elight.core.util;

import cz.encircled.elight.core.exception.RuntimeELightException;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Encircled on 16/09/2014.
 */
public class ReflectionUtil {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger();

    public static Method findAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Method method : getAllMethods(clazz)) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                return method;
            }
        }
        return null;
    }

    public static <T> T instance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeELightException(e);
        }
    }

    public static Object invokeMethod(Object instance, Method method, Object... params) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeELightException(e);
        }
    }

    public static void setField(Object instance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeELightException(e);
        }
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        addDeclaredFields(fields, clazz);
        for (Class<?> superClass : getSuperClasses(clazz)) {
            addDeclaredFields(fields, superClass);
        }
        return fields;
    }

    public static Field getFieldSafe(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            log.debug("Field {} not found on class {}", fieldName, clazz);
            return null;
        }
    }

    private static void addDeclaredFields(List<Field> fields, Class<?> sourceClazz) {
        for (Field f : sourceClazz.getDeclaredFields()) {
            fields.add(f);
        }
    }

    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        addDeclaredMethods(methods, clazz);
        for (Class<?> superClass : getSuperClasses(clazz)) {
            addDeclaredMethods(methods, superClass);
        }
        return methods;
    }

    private static void addDeclaredMethods(List<Method> methods, Class<?> sourceClazz) {
        for (Method m : sourceClazz.getDeclaredMethods()) {
            methods.add(m);
        }
    }

    public static Set<Class<?>> getSuperClasses(Class<?> clazz) {
        HashSet<Class<?>> collected = new HashSet<>();
        while (clazz.getSuperclass() != Object.class) {
            clazz = clazz.getSuperclass();
            collected.add(clazz);
        }
        return collected;
    }

    public static boolean isConcrete(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers()) && !clazz.isInterface();
    }

    public static Class<?> getClassOfType(Type type) {
        return type instanceof ParameterizedType ? (Class<?>) ((ParameterizedType) type).getRawType() : (Class<?>) type;
    }

    public static Type[] getTypesOfGenericClasses(Field field) {
        return getTypesOfGenericClasses(field.getGenericType());
    }

    public static Type[] getTypesOfGenericClasses(Type type) {
        // Check if type has generic (is parameterized)
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        } else {
            return new Type[]{};
        }
    }

    public static Class[] getGenericClasses(Field field) {
        Type[] types = getTypesOfGenericClasses(field);
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                classes[i] = (Class) parameterizedType.getRawType();
            } else {
                classes[i] = (Class) type;
            }
        }
        return classes;
    }

    public static Class[] getGenericClasses(Type type) {
        ParameterizedType genericType = (ParameterizedType) type;
        Type[] types = genericType.getActualTypeArguments();
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }
        return classes;
    }

}
