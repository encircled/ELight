package cz.encircled.elight.context;

import cz.encircled.elight.model.required.ComponentRequiredObjectTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/9/2015.
 */
public class NotRequiredWiringTest extends AbstractContextTest {

    public NotRequiredWiringTest() {
        super("cz.encircled.elight.model.required");
    }

    @Test
    public void notRequiredWiringTest() {
        ComponentRequiredObjectTest component = applicationContext.getComponent(ComponentRequiredObjectTest.class);

        Assert.assertNotNull(component);
        Assert.assertNull(component.resolvedObject);
        Assert.assertNull(component.house);
        Assert.assertEquals(0, component.resolvedObjectArray.length);
        Assert.assertEquals(0, component.resolvedObjectList.size());
        Assert.assertEquals(0, component.resolvedObjectsMap.size());
    }

}
