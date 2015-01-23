package cz.encircled.elight.context;

import cz.encircled.elight.model.house.AbstractHouse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kisel on 1/23/2015.
 */
public class FactoryCacheTest extends AbstractContextTest {

    @Test
    public void cacheStressTest() {
        Set<Object> set = new HashSet<>();
        for(int i = 0; i < 500; i++) {
            Assert.assertTrue(applicationContext.containsComponent(AbstractHouse.class));
            List<AbstractHouse> components = applicationContext.getComponents(AbstractHouse.class);
            Assert.assertEquals(1, components.size());
            set.addAll(components);
        }
        Assert.assertEquals(1, set.size());
    }

}
