package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.prototype.PrototypeComponentWithTime;
import cz.encircled.elight.model.prototype.SingletonWithPrototypeWiring;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class PrototypeScopeTest {

    @Test
    public void basicPrototypeTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model.prototype").initialize();
        PrototypeComponentWithTime component1 = context.getComponent(PrototypeComponentWithTime.class);
        PrototypeComponentWithTime component2 = (PrototypeComponentWithTime) context.getComponent("prototypeComponentWithTime");

        SingletonWithPrototypeWiring singleton = context.getComponent(SingletonWithPrototypeWiring.class);

        Assert.assertNotNull(singleton);
        Assert.assertNotNull(singleton.prototypeComponentWithTime);
        Assert.assertNotNull(component1);
        Assert.assertNotNull(component2);
        Assert.assertTrue(component1 != component2);
        Assert.assertNotEquals(component1.initTime, component2.initTime);
        Assert.assertNotEquals(singleton.prototypeComponentWithTime.initTime, component1.initTime);
        Assert.assertNotEquals(singleton.prototypeComponentWithTime.initTime, component2.initTime);
    }

}
