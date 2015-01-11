package cz.encircled.elight.context;

import cz.encircled.elight.model.creator.CreationTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Work on 1/7/2015.
 */
public class InstanceCreatorTest extends AbstractContextTest {

    public InstanceCreatorTest() {
        super("cz.encircled.elight.model.creator");
    }

    @Test
    public void basicComponentsTest() {
        CreationTester tester = applicationContext.getComponent(CreationTester.class);

        Assert.assertNotNull(tester);
        Assert.assertTrue(tester.createTestFlag);
    }

}
