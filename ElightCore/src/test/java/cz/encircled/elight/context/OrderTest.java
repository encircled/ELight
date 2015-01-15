package cz.encircled.elight.context;

import cz.encircled.elight.core.annotation.Order;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.house.Window;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/15/2015.
 */
public class OrderTest extends AbstractContextTest {

    @Test
    public void collectionsWithOrderTest() {
        House house = applicationContext.getComponent(House.class);
        Assert.assertNotNull(house.getWindows());
        for (int i = 0; i < house.getWindows().size(); i++) {
            Window window = house.getWindows().get(i);
            int order = window.getClass().getAnnotation(Order.class).value();
            Assert.assertEquals(i, order);
        }
    }

    @Test
    public void arrayWithOrderTest() {
        House house = applicationContext.getComponent(House.class);
        Window[] windowsArray = house.getWindowsArray();
        Assert.assertNotNull(windowsArray);
        for (int i = 0; i < windowsArray.length; i++) {
            Window window = windowsArray[i];
            int order = window.getClass().getAnnotation(Order.class).value();
            Assert.assertEquals(i, order);
        }
    }

}
