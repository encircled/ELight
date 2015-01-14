package cz.encircled.elight.core.util;

import cz.encircled.elight.core.exception.RuntimeELightException;

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

    public static Class[] getGenericClasses(Field field) {
        Type fieldType = field.getGenericType();
        // Check if field has generic
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type[] types = genericType.getActualTypeArguments();
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
        } else {
            return new Class[]{};
        }
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
