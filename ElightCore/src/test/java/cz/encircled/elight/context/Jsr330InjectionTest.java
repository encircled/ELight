package cz.encircled.elight.context;

import cz.encircled.elight.model.jsr330.Jsr330House;
import cz.encircled.elight.model.jsr330.Jsr330Room;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;

/**
 * Created by Encircled on 10-Jan-15.
 */
public class Jsr330InjectionTest extends AbstractContextTest {

    public Jsr330InjectionTest() {
        super("cz.encircled.elight.model.jsr330");
    }

    @Test
    public void scopeTest() {
        Jsr330House house = applicationContext.getComponent(Jsr330House.class);

        Assert.assertTrue(Jsr330House.class.getAnnotation(Singleton.class) != null);
        Assert.assertNotNull(house);
    }

    @Test
    public void basicInjectionTest() {
        Jsr330House house = applicationContext.getComponent(Jsr330House.class);
        Jsr330Room room = (Jsr330Room) applicationContext.getComponent("jsr330Room");

        Assert.assertNotNull(house);
        Assert.assertNotNull(room);

        Assert.assertNotNull(house.getJsr330Room());
    }

}
