package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.jsr330.Jsr330House;
import cz.encircled.elight.model.jsr330.Jsr330Room;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;

/**
 * Created by Encircled on 10-Jan-15.
 */
public class Jsr330InjectionTest {

    @Test
    public void scopeTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model.jsr330").initialize();
        Jsr330House house = context.getComponent(Jsr330House.class);

        Assert.assertTrue(Jsr330House.class.getAnnotation(Singleton.class) != null);
        Assert.assertNotNull(house);
    }

    @Test
    public void basicInjectionTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model.jsr330").initialize();
        Jsr330House house = context.getComponent(Jsr330House.class);
        Jsr330Room room = (Jsr330Room) context.getComponent("jsr330Room");

        Assert.assertNotNull(house);
        Assert.assertNotNull(room);

        Assert.assertNotNull(house.getJsr330Room());
    }

}
