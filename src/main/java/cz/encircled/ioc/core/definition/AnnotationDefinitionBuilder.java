package cz.encircled.ioc.core.definition;

import cz.encircled.ioc.annotation.*;
import cz.encircled.ioc.component.DependencyDescription;
import cz.encircled.ioc.component.creator.InstanceCreator;
import cz.encircled.ioc.core.context.ContextConstants;
import cz.encircled.ioc.util.ComponentUtil;
import cz.encircled.ioc.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Encircled on 20-Dec-14.
 */
public class AnnotationDefinitionBuilder extends DefinitionBuilder {

    public AnnotationDefinitionBuilder() {

    }

    @Override
    protected int getOrder(Class<?> clazz) {
        Order annotation = clazz.getAnnotation(Order.class);
        return annotation != null ? annotation.value() : ContextConstants.DEFAULT_ORDER;
    }

    @Override
    protected String getDestroyMethodName(Class<?> clazz) {
        return null;
    }

    @Override
    protected Method getInitMethod(Class<?> clazz) {
        return ReflectionUtil.findAnnotatedMethod(clazz, PostConstruct.class);
    }

    @Override
    protected String getName(Class<?> clazz) {
        Component annotation = clazz.getAnnotation(Component.class);
        return StringUtils.isEmpty(annotation.value()) ? ComponentUtil.getName(clazz) : annotation.value();
    }

    @Override
    protected List<DependencyDescription> getDependencyDescriptions(Class<?> clazz) {
        List<DependencyDescription> result = new ArrayList<>();
        List<Field> fields = ReflectionUtil.getAllFields(clazz);
        for (Field field : fields) {
            Wired wired = field.getAnnotation(Wired.class);
            if (wired != null) {
                result.add(new DependencyDescription(field, wired.isRequired()));
            }
        }
        return result;
    }

    @Override
    protected String getScope(Class<?> clazz) {
        Scope scope = clazz.getAnnotation(Scope.class);
        return scope != null ? scope.value() : null;
    }

    @Override
    protected Class<? extends InstanceCreator> getInstanceCreator(Class<?> clazz) {
        Factory factoryAnnotation = clazz.getAnnotation(Factory.class);
        return factoryAnnotation != null ? factoryAnnotation.value() : null;
    }

}
