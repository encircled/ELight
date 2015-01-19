package cz.encircled.elight.context;

import cz.encircled.elight.core.exception.ComponentNotFoundException;
import cz.encircled.elight.model.condition.FalseConditionComponent;
import cz.encircled.elight.model.condition.TrueConditionComponent;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/19/2015.
 */
public class ConditionalTest extends AbstractContextTest {

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalTest() {
        TrueConditionComponent trueCondition = applicationContext.getComponent(TrueConditionComponent.class);
        Assert.assertNotNull(trueCondition);
        applicationContext.getComponent(FalseConditionComponent.class);
    }

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalByNameTest() {
        TrueConditionComponent trueCondition = (TrueConditionComponent) applicationContext.getComponent("trueConditionComponent");
        Assert.assertNotNull(trueCondition);
        applicationContext.getComponent("falseConditionComponent");
    }

}
