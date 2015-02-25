package cz.encircled.elight.util;

import cz.encircled.elight.core.util.CollectionUtil;
import cz.encircled.elight.core.util.ComponentUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Test
    public void isEmptyTest() {
        Assert.assertTrue(CollectionUtil.isEmpty(new Object[]{}));
        Assert.assertFalse(CollectionUtil.isEmpty(new Object[]{1}));
        Assert.assertTrue(CollectionUtil.isEmpty(Arrays.asList()));
        Assert.assertFalse(CollectionUtil.isEmpty(Arrays.asList(1)));
    }

    @Test
    public void getAppropriateMapMapTest() {
        Map<Object, Object> appropriateMap = new HashMap<>();
        Assert.assertEquals(HashMap.class, CollectionUtil.getAppropriateMap((Class<? extends Map<Object, Object>>) appropriateMap.getClass()).getClass());

        appropriateMap = new ConcurrentHashMap<>();
        Assert.assertEquals(ConcurrentHashMap.class, CollectionUtil.getAppropriateMap((Class<? extends Map<Object, Object>>) appropriateMap.getClass()).getClass());
    }

    public void getComponentTest() {

    }

}
