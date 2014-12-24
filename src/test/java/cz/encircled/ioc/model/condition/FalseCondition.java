package cz.encircled.ioc.model.condition;

import cz.encircled.ioc.core.ComponentCondition;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class FalseCondition implements ComponentCondition {

    @Override
    public boolean addToContext(Class<?> clazz) {
        return false;
    }

}
