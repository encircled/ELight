package cz.encircled.elight.context;

import cz.encircled.elight.core.util.ComponentUtil;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.house.Window;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Kisel on 1/19/2015.
 */
public class MapWiringTest extends AbstractContextTest {

    @Test
    public void mapWithStringTest() {
        House house = applicationContext.getComponent(House.class);
        Map<Window, String> windowAsKeyMap = house.getWindowAsKeyMap();
        Assert.assertNotNull(windowAsKeyMap);
        Assert.assertTrue(windowAsKeyMap.size() > 0);

        Map<String, Window> windowAsValueMap = house.getWindowAsValueMap();
        Assert.assertNotNull(windowAsValueMap);
        Assert.assertTrue(windowAsValueMap.size() > 0);

        windowAsKeyMap.forEach((k, v) -> {
            Assert.assertEquals(ComponentUtil.getDefaultName(k.getClass()), v);
        });

        windowAsValueMap.forEach((k, v) -> {
            Assert.assertEquals(ComponentUtil.getDefaultName(v.getClass()), k);
        });
    }

}
