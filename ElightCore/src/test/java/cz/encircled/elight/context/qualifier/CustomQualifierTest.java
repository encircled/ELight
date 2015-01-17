package cz.encircled.elight.context.qualifier;

import cz.encircled.elight.context.AbstractContextTest;
import cz.encircled.elight.model.qualifier.custom.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class CustomQualifierTest extends AbstractContextTest {

    public CustomQualifierTest() {
        super("cz.encircled.elight.model.qualifier.custom");
    }

    @Test
    public void qualifierViaFieldTest() {
        CustomQualifierWiring component = applicationContext.getComponent(CustomQualifierWiring.class);

        Assert.assertNotNull(component);
        Assert.assertNotNull(component.getBlack());
        Assert.assertTrue(component.getBlack() instanceof BlackCustomQualifier);
        Assert.assertNotNull(component.getWhite());
        Assert.assertTrue(component.getWhite() instanceof WhiteCustomQualifier);
        Assert.assertNotNull(component.getRed());
        Assert.assertTrue(component.getRed() instanceof RedCustomQualifier);
        Assert.assertNotNull(component.getBlackAndWhite());
        Assert.assertTrue(component.getBlackAndWhite() instanceof BlackAndWhiteCustomQualifier);
    }

    @Test
    public void qualifierViaSetterTest() {
        CustomQualifierWiring component = applicationContext.getComponent(CustomQualifierWiring.class);

        Assert.assertNotNull(component);
        Assert.assertNotNull(component.getMethodBlackAndWhite());
        Assert.assertTrue(component.getMethodBlackAndWhite() instanceof BlackAndWhiteCustomQualifier);
    }

    @Test
    public void qualifierViaGetterTest() {
        CustomQualifierWiring component = applicationContext.getComponent(CustomQualifierWiring.class);

        Assert.assertNotNull(component);
        Assert.assertNotNull(component.getGetterBlackAndWhite());
        Assert.assertTrue(component.getGetterBlackAndWhite() instanceof BlackAndWhiteCustomQualifier);
    }

}
