package cz.encircled.elight.util;

import cz.encircled.elight.core.util.ComponentUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/17/2015.
 */
public class ComponentUtilTest {

    @Test
    public void testDefaultName() {
        Assert.assertEquals("componentUtilTest", ComponentUtil.getDefaultName(this.getClass()));
    }

    @Test
    public void testFieldFromGetter() throws NoSuchMethodException {
        Assert.assertEquals("componentTest", ComponentUtil.getFieldNameFromGetter(this.getClass().getMethod("getComponentTest")));
    }

    public void getComponentTest() {

    }

}
