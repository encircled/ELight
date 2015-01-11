package cz.encircled.elight.context;

import cz.encircled.elight.core.annotation.Order;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.core.exception.ComponentNotFoundException;
import cz.encircled.elight.model.condition.FalseConditionComponent;
import cz.encircled.elight.model.condition.TrueConditionComponent;
import cz.encircled.elight.model.house.Building;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.house.Window;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

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
    public void mapsTest() {
        House house = applicationContext.getComponent(House.class);
        Map<Window, String> windowAsKeyMap = house.getWindowAsKeyMap();
        Assert.assertNotNull(windowAsKeyMap);
        Assert.assertTrue(windowAsKeyMap.size() > 0);

        Map<String, Window> windowAsValueMap = house.getWindowAsValueMap();
        Assert.assertNotNull(windowAsValueMap);
        Assert.assertTrue(windowAsValueMap.size() > 0);
    }

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

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalTest() {
        TrueConditionComponent trueCondition = applicationContext.getComponent(TrueConditionComponent.class);
        Assert.assertNotNull(trueCondition);
        applicationContext.getComponent(FalseConditionComponent.class);
    }

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalByNameTest() {
        TrueConditionComponent trueCondition = (TrueConditionComponent) applicationContext.getComponent("trueConditionComponent");
        Assert.assertNotNull(trueCondition);
        applicationContext.getComponent("falseConditionComponent");
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
