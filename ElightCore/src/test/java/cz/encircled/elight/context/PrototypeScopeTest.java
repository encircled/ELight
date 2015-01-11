package cz.encircled.elight.context;

import cz.encircled.elight.model.prototype.PrototypeComponentWithTime;
import cz.encircled.elight.model.prototype.SingletonWithPrototypeWiring;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class PrototypeScopeTest extends AbstractContextTest {

    public PrototypeScopeTest() {
        super("cz.encircled.elight.model.prototype");
    }

    @Test
    public void basicPrototypeTest() {
        PrototypeComponentWithTime component1 = applicationContext.getComponent(PrototypeComponentWithTime.class);
        PrototypeComponentWithTime component2 = (PrototypeComponentWithTime) applicationContext.getComponent("prototypeComponentWithTime");

        SingletonWithPrototypeWiring singleton = applicationContext.getComponent(SingletonWithPrototypeWiring.class);

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
