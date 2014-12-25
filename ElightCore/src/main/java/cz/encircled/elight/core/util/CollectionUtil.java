package cz.encircled.elight.core.util;

import cz.encircled.elight.core.exception.RuntimeELightException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static <T> Collection<T> getAppropriateCollection(Class<? extends Collection<T>> clazz) {
        if (ReflectionUtil.isConcrete(clazz)) {
            return ReflectionUtil.instance(clazz);
        } else if (List.class.isAssignableFrom(clazz)) {
            return new ArrayList<>();
        } else if (Set.class.isAssignableFrom(clazz)) {
            return new HashSet<>();
        } else if (Queue.class.isAssignableFrom(clazz)) {
            return new LinkedList<>();
        }
        throw new RuntimeELightException("Can't find appropriate collection for class " + clazz);
    }

    public static <K, V> Map<K, V> getAppropriateMap(Class<? extends Map<K, V>> clazz) {
        if (ReflectionUtil.isConcrete(clazz)) {
            return ReflectionUtil.instance(clazz);
        } else if (ConcurrentMap.class.isAssignableFrom(clazz)) {
            return new ConcurrentHashMap<>();
        } else {
            return new HashMap<>();
        }
    }

    public static <K, V> Map<K, V> collectionToMap(Collection<K> sourceCollection) {
        return collectionToMap(sourceCollection, new HashMap<>());
    }

    public static <K, V> Map<K, V> collectionToMap(Collection<K> sourceCollection, Map<K, V> targetMap) {
        if (isEmpty(sourceCollection)) {
            return targetMap;
        }
        sourceCollection.stream().forEach(e -> targetMap.put(e, null));
        return targetMap;
    }

    public static String stringify(Object[] array) {
        if (isEmpty(array)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder("{ ");
            for (Object obj : array)
                sb.append(obj).append(", ");
            return sb.deleteCharAt(sb.length() - 2).append("}").toString();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] collectionToArray(Collection<T> sourceCollection, Class<?> elementClass) {
        Object array = Array.newInstance(elementClass, sourceCollection.size());
        Iterator iterator = sourceCollection.iterator();
        for (int i = 0; i < sourceCollection.size(); i++) {
            Array.set(array, i, iterator.next());
        }
        return (T[]) array;
    }

}
