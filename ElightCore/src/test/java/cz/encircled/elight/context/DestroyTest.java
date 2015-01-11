package cz.encircled.elight.context;

import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Work on 1/8/2015.
 */
public class DestroyTest extends AbstractContextTest {

    @Test
    public void destroyCalledTest() {
        House component = applicationContext.getComponent(House.class);

        Assert.assertNotNull(component);
        Assert.assertFalse(component.isDestroyCalled());
        applicationContext.destroy();
        Assert.assertTrue(component.isDestroyCalled());
    }

}
