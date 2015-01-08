package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Work on 1/8/2015.
 */
public class DestroyTest {

    private static ApplicationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationApplicationContext("cz.encircled.elight.model").initialize();
    }

    @Test
    public void destroyCalledTest() {
        House component = context.getComponent(House.class);

        Assert.assertNotNull(component);
        Assert.assertFalse(component.isDestroyCalled());
        context.destroy();
        Assert.assertTrue(component.isDestroyCalled());
    }

}
