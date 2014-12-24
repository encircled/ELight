package cz.encircled.elight.util;

import cz.encircled.elight.core.exception.RuntimeELightException;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public static <T> T instance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeELightException(e);
        }
    }

    public static Object invokeMethod(Method method, Object instance) {
        try {
            method.setAccessible(true);
            return method.invoke(instance);
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

    @NotNull
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
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] types = genericType.getActualTypeArguments();
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }
        return classes;
    }

}
