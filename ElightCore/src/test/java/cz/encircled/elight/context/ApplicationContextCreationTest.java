package cz.encircled.elight.context;

import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.house.Building;
import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ApplicationContextCreationTest extends AbstractContextTest {

    @Test
    public void basicComponentsTest() {
        Object testComponentObj = applicationContext.getComponent("house");
        Object doorCustomName = applicationContext.getComponent("mainDoor");
        House house = applicationContext.getComponent(House.class);
        Building testComponentInt = applicationContext.getComponent(Building.class);

        Assert.assertNotNull(doorCustomName);
        Assert.assertNotNull(testComponentObj);
        Assert.assertNotNull(house);
        Assert.assertNotNull(testComponentInt);

        Assert.assertNotNull(house.getDoor());
        Assert.assertTrue(house.isInitCalled());
    }

    @Test
    public void arrayTest() {
        House house = applicationContext.getComponent(House.class);
        Assert.assertNotNull(house.getWindowsArray());
        Assert.assertTrue(house.getWindowsArray().length > 0);
    }

    @Test
    public void containsComponentTest() {
        Assert.assertTrue(applicationContext.containsComponent(ApplicationContext.class));
        Assert.assertTrue(applicationContext.containsComponent("applicationContext"));

        Assert.assertTrue(applicationContext.containsComponent("house"));

        Assert.assertFalse(applicationContext.containsComponent("notExistingComponent"));
        Assert.assertFalse(applicationContext.containsComponent(this.getClass()));
    }

}
