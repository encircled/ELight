package cz.encircled.ioc.model.condition;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.component.condition.ComponentCondition;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
public class FalseCondition implements ComponentCondition {

    @Override
    public boolean addToContext(Class<?> clazz) {
        return false;
    }

}
