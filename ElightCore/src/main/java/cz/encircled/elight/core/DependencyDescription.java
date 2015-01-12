package cz.encircled.elight.core;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class DependencyDescription {

    public DependencyInjectionType dependencyInjectionType;

    public Field targetField;

    public Method targetMethod;

    public Type targetType;

    public Class<?> targetClass;

    public boolean isRequired = true;

    public String nameQualifier;

    public Object[] qualifiers;

    public DependencyDescription(boolean isRequired, String nameQualifier, Object[] qualifiers) {
        this.isRequired = isRequired;
        this.nameQualifier = nameQualifier;
        this.qualifiers = qualifiers;
    }

    public boolean hasNameQualifier() {
        return StringUtils.isNotEmpty(nameQualifier);
    }

    @Override
    public String toString() {
        return "DependencyDescription{" +
                "targetField=" + targetField +
                ", targetMethod=" + targetMethod +
                '}';
    }
}
