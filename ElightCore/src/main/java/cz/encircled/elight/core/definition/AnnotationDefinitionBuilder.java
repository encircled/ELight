package cz.encircled.elight.core.definition;

import cz.encircled.elight.core.ComponentPostProcessor;
import cz.encircled.elight.core.DependencyDescription;
import cz.encircled.elight.core.DependencyInjectionType;
import cz.encircled.elight.core.annotation.*;
import cz.encircled.elight.core.annotation.Scope;
import cz.encircled.elight.core.context.ContextConstants;
import cz.encircled.elight.core.creator.InstanceCreator;
import cz.encircled.elight.core.exception.RawTypeException;
import cz.encircled.elight.core.exception.RuntimeELightException;
import cz.encircled.elight.core.util.ComponentUtil;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Encircled on 20-Dec-14.
 */
public class AnnotationDefinitionBuilder extends AbstractDefinitionBuilder {

    @Override
    protected Object[] getQualifiers(Class<?> clazz) {
        return findQualifiers(clazz.getAnnotations());
    }

    @Override
    protected int getOrder(Class<?> clazz) {
        Order annotation = clazz.getAnnotation(Order.class);
        return annotation != null ? annotation.value() : ContextConstants.DEFAULT_ORDER;
    }

    @Override
    protected Method getDestroyMethod(Class<?> clazz) {
        return ReflectionUtil.findAnnotatedMethod(clazz, PreDestroy.class);
    }

    @Override
    protected Method getInitMethod(Class<?> clazz) {
        return ReflectionUtil.findAnnotatedMethod(clazz, PostConstruct.class);
    }

    @Override
    public boolean checkCandidate(Class<?> clazz) {
        boolean isAnnotated = clazz.getAnnotation(Component.class) != null || clazz.getAnnotation(Singleton.class) != null;
        return isAnnotated && ReflectionUtil.isConcrete(clazz) && isConditionTrue(clazz);
    }

    @Override
    public boolean isPostProcessor(Class<?> clazz) {
        return ComponentPostProcessor.class.isAssignableFrom(clazz) && ReflectionUtil.isConcrete(clazz);
    }

    @Override
    public boolean isConditionTrue(Class<?> clazz) {
        Conditional conditional = clazz.getAnnotation(Conditional.class);
        return conditional == null || ReflectionUtil.instance(conditional.value()).addToContext(clazz);
    }

    @Override
    public String getName(Class<?> clazz) {
        Component annotation = clazz.getAnnotation(Component.class);
        Named namedAnnotation = clazz.getAnnotation(Named.class);
        if (annotation != null && StringUtils.isNotEmpty(annotation.value())) {
            return annotation.value();
        }
        return namedAnnotation == null || StringUtils.isEmpty(namedAnnotation.value()) ?
                ComponentUtil.getDefaultName(clazz) : namedAnnotation.value();
    }

    @Override
    protected List<DependencyDescription> getDependencyDescriptions(Class<?> clazz) {
        List<DependencyDescription> result = new ArrayList<>();
        for (Field field : ReflectionUtil.getAllFields(clazz)) {
            DependencyDescription dependencyDescription = buildDependencyDescription(field);
            if(dependencyDescription != null) {
                Class<?> fieldType = field.getType();
                if(fieldType.equals(Provider.class)) {
                    dependencyDescription.isProvider = true;
                    Type[] typesOfGenericClasses = ReflectionUtil.getTypesOfGenericClasses(field);
                    if(typesOfGenericClasses.length == 0)
                        throw new RawTypeException("Error in " + clazz.getName() + ". Provider must have generic type specified.");
                    dependencyDescription.targetClass = ReflectionUtil.getClassOfType(typesOfGenericClasses[0]);
                    dependencyDescription.targetType = typesOfGenericClasses[0];
                } else {
                    dependencyDescription.targetClass = field.getType();
                    dependencyDescription.targetType = field.getGenericType();
                }
                dependencyDescription.targetField = field;
                dependencyDescription.dependencyInjectionType = DependencyInjectionType.FIELD;
                result.add(dependencyDescription);
            }
        }
        for (Method method : ReflectionUtil.getAllMethods(clazz)) {
            DependencyDescription dependencyDescription = buildDependencyDescription(method);
            if(dependencyDescription != null) {
                if(method.getParameterCount() != 1) {
                    throw new RuntimeELightException("Method for wiring must have one parameter : " + method);
                }
                dependencyDescription.targetType = method.getParameters()[0].getParameterizedType();
                dependencyDescription.targetClass = method.getParameterTypes()[0];
                dependencyDescription.targetMethod = method;
                dependencyDescription.dependencyInjectionType = DependencyInjectionType.METHOD;
                result.add(dependencyDescription);
            }
        }
        return result;
    }

    private DependencyDescription buildDependencyDescription(AccessibleObject field) {
        Wired wired = field.getAnnotation(Wired.class);
        if (wired != null) {
            Object[] qualifiers = findQualifiers(field.getAnnotations());
            String named = getValueFromNamedAnnotation(field);
            String finalName = StringUtils.isEmpty(named) ? wired.name() : named;
            return new DependencyDescription(wired.required(), finalName, qualifiers);
        } else {
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                Object[] qualifiers = findQualifiers(field.getAnnotations());
                return new DependencyDescription(true, getValueFromNamedAnnotation(field), qualifiers);
            }
        }
        return null;
    }

    @Override
    protected String getScope(Class<?> clazz) {
        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        if (clazz.getAnnotation(Singleton.class) != null) {
            return Scope.PROTOTYPE;
        }
        return null;
    }

    @Override
    protected Class<? extends InstanceCreator> getInstanceCreator(Class<?> clazz) {
        Creator annotation = clazz.getAnnotation(Creator.class);
        return annotation != null ? annotation.value() : null;
    }

    private String getValueFromNamedAnnotation(AccessibleObject accessibleObject) {
        Named namedAnnotation = accessibleObject.getAnnotation(Named.class);
        return namedAnnotation == null ? null : namedAnnotation.value();
    }

    private Object[] findQualifiers(Annotation[] annotations) {
        List<Object> qualifiers = new ArrayList<>(2);
        for (Annotation fieldAnnotation : annotations) {
            if (fieldAnnotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                Method[] methods = fieldAnnotation.annotationType().getDeclaredMethods();
                if (methods.length != 1) {
                    throw new RuntimeELightException("Qualifier annotation must have one method - " +
                            fieldAnnotation.getClass().toString());
                }
                Object value = ReflectionUtil.invokeMethod(fieldAnnotation, methods[0]);
                if(value != null)
                    qualifiers.add(value);
            }
        }
        return qualifiers.toArray();
    }

}
