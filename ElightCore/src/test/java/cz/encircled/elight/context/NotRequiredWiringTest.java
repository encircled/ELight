package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.required.ComponentRequiredObjectTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/9/2015.
 */
public class NotRequiredWiringTest {

    @Test
    public void notRequiredWiringTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model.required").initialize();
        ComponentRequiredObjectTest component = context.getComponent(ComponentRequiredObjectTest.class);

        Assert.assertNotNull(component);
        Assert.assertNull(component.resolvedObject);
        Assert.assertNull(component.house);
        Assert.assertEquals(0, component.resolvedObjectArray.length);
        Assert.assertEquals(0, component.resolvedObjectList.size());
        Assert.assertEquals(0, component.resolvedObjectsMap.size());
    }

}
