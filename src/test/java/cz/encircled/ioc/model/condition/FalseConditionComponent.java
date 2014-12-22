package cz.encircled.ioc.model.condition;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.annotation.Conditional;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
@Conditional(FalseCondition.class)
public class FalseConditionComponent {


}
