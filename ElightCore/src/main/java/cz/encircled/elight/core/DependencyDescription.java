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

    public Object[] qualifiers;

    public DependencyDescription(Field targetField, boolean isRequired, String nameQualifier, Object[] qualifiers) {
        this.targetField = targetField;
        this.isRequired = isRequired;
        this.nameQualifier = nameQualifier;
        this.qualifiers = qualifiers;
    }

    public boolean hasNameQualifier() {
        return StringUtils.isNotEmpty(nameQualifier);
    }

}
