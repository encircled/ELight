package cz.encircled.elight.core.definition;

import cz.encircled.elight.core.ComponentPostProcessor;
import cz.encircled.elight.core.DependencyDescription;
import cz.encircled.elight.core.annotation.*;
import cz.encircled.elight.core.context.ContextConstants;
import cz.encircled.elight.core.creator.InstanceCreator;
import cz.encircled.elight.core.exception.RuntimeELightException;
import cz.encircled.elight.core.util.ComponentUtil;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Encircled on 20-Dec-14.
 */
public class AnnotationDefinitionBuilder extends AbstractDefinitionBuilder {

    @Override
    protected Object getQualifier(Class<?> clazz) {
        return findQualifier(clazz.getAnnotations());
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
        List<Field> fields = ReflectionUtil.getAllFields(clazz);
        for (Field field : fields) {
            Wired wired = field.getAnnotation(Wired.class);
            if (wired != null) {
                Object qualifier = findQualifier(field.getAnnotations());
                String named = getValueFromNamedAnnotation(field);
                String finalName = StringUtils.isEmpty(named) ? wired.name() : named;
                result.add(new DependencyDescription(field, wired.required(), finalName, qualifier));
            } else {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    Object qualifier = findQualifier(field.getAnnotations());
                    result.add(new DependencyDescription(field, true, getValueFromNamedAnnotation(field), qualifier));
                }
            }
        }
        return result;
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

    private String getValueFromNamedAnnotation(Field field) {
        Named namedAnnotation = field.getAnnotation(Named.class);
        return namedAnnotation == null ? null : namedAnnotation.value();
    }

    private Object findQualifier(Annotation[] annotations) {
        Object value = null;
        for (Annotation fieldAnnotation : annotations) {
            if (fieldAnnotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                Method[] methods = fieldAnnotation.annotationType().getDeclaredMethods();
                if (methods.length != 1) {
                    throw new RuntimeELightException("Qualifier annotation must have one method - " +
                            fieldAnnotation.getClass().toString());
                }
                value = ReflectionUtil.invokeMethod(methods[0], fieldAnnotation);
                break;
            }
        }
        return value;
    }

}
