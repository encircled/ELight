package cz.encircled.ioc.model.condition;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.core.ComponentCondition;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
public class TrueCondition implements ComponentCondition {

    @Override
    public boolean addToContext(Class<?> clazz) {
        return true;
    }
}
