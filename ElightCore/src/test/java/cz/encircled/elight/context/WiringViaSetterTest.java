package cz.encircled.elight.context;

import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/12/2015.
 */
public class WiringViaSetterTest extends AbstractContextTest {

    @Test
    public void basicSetterWiringTest() {
        House house = applicationContext.getComponent(House.class);

        Assert.assertNotNull(house);
        Assert.assertNotNull(house.getDoorVia330Setter());
        Assert.assertNotNull(house.getDoorViaSetter());

        Assert.assertNotNull(house.getWindowAsKeyMapVia330Setter());
        Assert.assertNotNull(house.getWindowAsKeyMapViaSetter());

        Assert.assertNotNull(house.getWindowAsValueMapVia330Setter());
        Assert.assertNotNull(house.getWindowAsValueMapViaSetter());

        Assert.assertNotNull(house.getWindowsArrayVia330Setter());
        Assert.assertNotNull(house.getWindowsArrayViaSetter());

        Assert.assertNotNull(house.getWindowsVia330Setter());
        Assert.assertNotNull(house.getWindowsViaSetter());


    }

}
