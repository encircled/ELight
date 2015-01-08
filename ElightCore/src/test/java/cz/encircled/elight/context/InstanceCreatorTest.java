package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.model.creator.CreationTester;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Work on 1/7/2015.
 */
public class InstanceCreatorTest {

    private static AnnotationApplicationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationApplicationContext("cz.encircled.elight.model.creator");
    }

    @Test
    public void basicComponentsTest() {
        CreationTester tester = context.getComponent(CreationTester.class);

        Assert.assertNotNull(tester);
        Assert.assertTrue(tester.createTestFlag);
    }

}
