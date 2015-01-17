package cz.encircled.elight.core.util;

import cz.encircled.elight.core.exception.WrongGetterException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ComponentUtil {

    private static final String getterPrefix = "get";

    public static String getDefaultName(Class<?> clazz) {
        return StringUtils.uncapitalize(clazz.getSimpleName());
    }

    public static String getFieldNameFromGetter(Method getterMethod) {
        String methodName = getterMethod.getName();
        if(!methodName.startsWith(getterPrefix)) {
            throw new WrongGetterException(getterMethod);
        }
        return StringUtils.uncapitalize(methodName.substring(3));
    }

}
