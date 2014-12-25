package cz.encircled.elight.model.condition;


import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Conditional;

/**
 * Created by Encircled on 22-Dec-14.
 */
@Component
@Conditional(FalseCondition.class)
public class FalseConditionComponent {

}
