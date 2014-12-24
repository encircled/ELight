package cz.encircled.elight.model.condition;


import cz.encircled.elight.core.ComponentCondition;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class FalseCondition implements ComponentCondition {

    @Override
    public boolean addToContext(Class<?> clazz) {
        return false;
    }

}
