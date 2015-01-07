package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationContext;
import cz.encircled.elight.model.creator.CreationTester;
import cz.encircled.elight.model.house.Building;
import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Work on 1/7/2015.
 */
public class InstanceCreatorTest {

    private static AnnotationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationContext("cz.encircled.elight.model.creator");
    }

    @Test
    public void basicComponentsTest() {
        CreationTester tester = context.getComponent(CreationTester.class);

        Assert.assertNotNull(tester);
        Assert.assertTrue(tester.createTestFlag);
    }

}
