package cz.encircled.ioc.core;

import java.lang.reflect.Field;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class DependencyDescription {

    public Field targetField;

    public boolean isRequired = true;

    public DependencyDescription(Field targetField, boolean isRequired) {
        this.targetField = targetField;
        this.isRequired = isRequired;
    }

}
