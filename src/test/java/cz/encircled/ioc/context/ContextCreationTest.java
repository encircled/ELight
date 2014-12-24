package cz.encircled.ioc.context;

import cz.encircled.ioc.annotation.Order;
import cz.encircled.ioc.core.context.AnnotationContext;
import cz.encircled.ioc.core.exception.ComponentNotFoundException;
import cz.encircled.ioc.model.condition.FalseConditionComponent;
import cz.encircled.ioc.model.condition.TrueConditionComponent;
import cz.encircled.ioc.model.house.Building;
import cz.encircled.ioc.model.house.House;
import cz.encircled.ioc.model.house.Window;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ContextCreationTest {

    private static AnnotationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationContext("cz.encircled.ioc");
    }

    @Test
    public void basicComponentsTest() {
        Object testComponentObj = context.getComponent("house");
        Object doorCustomName = context.getComponent("mainDoor");
        House house = context.getComponent(House.class);
        Building testComponentInt = context.getComponent(Building.class);

        Assert.assertNotNull(doorCustomName);
        Assert.assertNotNull(testComponentObj);
        Assert.assertNotNull(house);
        Assert.assertNotNull(testComponentInt);

        Assert.assertNotNull(house.getDoor());
        Assert.assertTrue(house.isInitCalled());
    }

    @Test
    public void mapsTest() {
        House house = context.getComponent(House.class);
        Map<Window, String> windowAsKeyMap = house.getWindowAsKeyMap();
        Assert.assertNotNull(windowAsKeyMap);
        Assert.assertTrue(windowAsKeyMap.size() > 0);

        Map<String, Window> windowAsValueMap = house.getWindowAsValueMap();
        Assert.assertNotNull(windowAsValueMap);
        Assert.assertTrue(windowAsValueMap.size() > 0);
    }

    @Test
    public void collectionsWithOrderTest() {
        House house = context.getComponent(House.class);
        Assert.assertNotNull(house.getWindows());
        for (int i = 0; i < house.getWindows().size(); i++) {
            Window window = house.getWindows().get(i);
            int order = window.getClass().getAnnotation(Order.class).value();
            Assert.assertEquals(i, order);
        }
    }

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalTest() {
        TrueConditionComponent trueCondition = context.getComponent(TrueConditionComponent.class);
        Assert.assertNotNull(trueCondition);
        context.getComponent(FalseConditionComponent.class);
    }

    @Test(expected = ComponentNotFoundException.class)
    public void conditionalByNameTest() {
        TrueConditionComponent trueCondition = (TrueConditionComponent) context.getComponent("trueConditionComponent");
        Assert.assertNotNull(trueCondition);
        context.getComponent("falseConditionComponent");
    }

    @Test
    public void arrayTest() {
        House house = context.getComponent(House.class);
        Assert.assertNotNull(house.getWindowsArray());
        Assert.assertTrue(house.getWindowsArray().length > 0);
    }

}
