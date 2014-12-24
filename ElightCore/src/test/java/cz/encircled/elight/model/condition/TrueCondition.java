package cz.encircled.elight.model.condition;


import cz.encircled.elight.annotation.Component;
import cz.encircled.elight.core.ComponentCondition;

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
