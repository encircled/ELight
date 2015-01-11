package cz.encircled.elight.context.qualifier;

import cz.encircled.elight.context.AbstractContextTest;
import cz.encircled.elight.model.qualifier.name.WiredNameComponent;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class NameQualifierTest extends AbstractContextTest {

    public NameQualifierTest() {
        super("cz.encircled.elight.model.qualifier.name");
    }

    @Test
    public void testWiringByCustomName() {
        WiredNameComponent nameComponent = applicationContext.getComponent(WiredNameComponent.class);

        Assert.assertNotNull(applicationContext.getComponent("anotherCustomComponentName"));
        Assert.assertNotNull(applicationContext.getComponent("customComponentName"));

        Assert.assertNotNull(nameComponent);
        Assert.assertNotNull(nameComponent.getNamedComponentToTest());
        Assert.assertNotNull(nameComponent.getJsr330NamedComponentToTest());
    }

}
