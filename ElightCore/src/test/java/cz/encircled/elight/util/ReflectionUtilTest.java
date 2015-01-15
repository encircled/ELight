package cz.encircled.elight.util;

import cz.encircled.elight.core.context.AbstractApplicationContext;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Kisel on 1/14/2015.
 */
public class ReflectionUtilTest {

    public List<String> stringGenericField;

    public List rawListField;

    public Map<String, Long> mapGenericField;

    public Map<String, List<String>> mapWithInnerGenericField;

    @Test
    public void testIsConcrete() {
        Assert.assertTrue(ReflectionUtil.isConcrete(this.getClass()));
        Assert.assertTrue(ReflectionUtil.isConcrete(String.class));
        Assert.assertFalse(ReflectionUtil.isConcrete(Closeable.class));
        Assert.assertFalse(ReflectionUtil.isConcrete(AbstractApplicationContext.class));
    }

    @Test
    public void testGetGenericFromField() {
        Class[] stringGenericFields = ReflectionUtil.getGenericClasses(getField("stringGenericField"));
        Assert.assertNotNull(stringGenericFields);
        Assert.assertEquals(1, stringGenericFields.length);
        Assert.assertEquals(String.class, stringGenericFields[0]);

        Class[] mapGenericFields = ReflectionUtil.getGenericClasses(getField("mapGenericField"));
        Assert.assertNotNull(mapGenericFields);
        Assert.assertEquals(2, mapGenericFields.length);
        Assert.assertEquals(String.class, mapGenericFields[0]);
        Assert.assertEquals(Long.class, mapGenericFields[1]);

        Class[] mapWithInnerGenericFields = ReflectionUtil.getGenericClasses(getField("mapWithInnerGenericField"));
        Assert.assertNotNull(mapWithInnerGenericFields);
        Assert.assertEquals(2, mapWithInnerGenericFields.length);
        Assert.assertEquals(String.class, mapWithInnerGenericFields[0]);
        Assert.assertEquals(List.class, mapWithInnerGenericFields[1]);

        Class[] rawListFields = ReflectionUtil.getGenericClasses(getField("rawListField"));
        Assert.assertNotNull(rawListFields);
        Assert.assertEquals(0, rawListFields.length);
    }

    private Field getField(String name) {
        try {
            return this.getClass().getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field " + name + " not found here");
        }
    }

}
