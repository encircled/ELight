package cz.encircled.elight.core;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class DependencyDescription {

    public Field targetField;

    public boolean isRequired = true;

    public String nameQualifier;

    public Object qualifier;

    public DependencyDescription(Field targetField, boolean isRequired) {
        this.targetField = targetField;
        this.isRequired = isRequired;
    }

    public DependencyDescription(Field targetField, boolean isRequired, String nameQualifier) {
        this.targetField = targetField;
        this.isRequired = isRequired;
        this.nameQualifier = nameQualifier;
    }

    public boolean hasNameQualifier() {
        return StringUtils.isNotEmpty(nameQualifier);
    }

}
