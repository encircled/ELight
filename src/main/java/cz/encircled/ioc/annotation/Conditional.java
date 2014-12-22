package cz.encircled.ioc.annotation;

import cz.encircled.ioc.component.condition.ComponentCondition;

/**
 * Created by Encircled on 22-Dec-14.
 */
public @interface Conditional {

    Class<? extends ComponentCondition> value();

}
