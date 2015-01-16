package cz.encircled.elight.context.qualifier;

import cz.encircled.elight.context.AbstractContextTest;
import cz.encircled.elight.model.qualifier.custom.CustomQualifierWiring;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class CustomQualifierTest extends AbstractContextTest {

    public CustomQualifierTest() {
        super("cz.encircled.elight.model.qualifier.custom");
    }

    // TODO test methods injection
    @Test
    public void basicQualifierTest() {
        CustomQualifierWiring component = applicationContext.getComponent(CustomQualifierWiring.class);

        Assert.assertNotNull(component);
        Assert.assertNotNull(component.getBlack());
        Assert.assertNotNull(component.getWhite());
        Assert.assertNotNull(component.getRed());
        Assert.assertNotNull(component.getBlackAndWhite());
    }

}
