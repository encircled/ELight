package cz.encircled.ioc.container;

import cz.encircled.ioc.annotation.Order;
import cz.encircled.ioc.core.context.AnnotationContext;
import cz.encircled.ioc.exception.ComponentNotFoundException;
import cz.encircled.ioc.model.Building;
import cz.encircled.ioc.model.House;
import cz.encircled.ioc.model.Window;
import cz.encircled.ioc.model.condition.FalseCondition;
import cz.encircled.ioc.model.condition.TrueCondition;
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
        context = new AnnotationContext("cz");
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
        TrueCondition trueCondition = context.getComponent(TrueCondition.class);
        Assert.assertNotNull(trueCondition);
        context.getComponent(FalseCondition.class);
    }

    @Test
    public void arrayTest() {
        House house = context.getComponent(House.class);
        Assert.assertNotNull(house.getWindowsArray());
        Assert.assertTrue(house.getWindowsArray().length > 0);
    }

}
