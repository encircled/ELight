package cz.encircled.ioc.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ComponentUtil {

    public static String getName(Class<?> clazz) {
        return StringUtils.uncapitalize(clazz.getSimpleName());
    }

}
