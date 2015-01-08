package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.resolved.ResolvedObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Work on 1/8/2015.
 */
public class ResolvedDependenciesTest {

    private static AnnotationApplicationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationApplicationContext("cz.encircled.elight");
    }

    @Test
    public void testBasicResolvedDependencies() {
        ApplicationContext component = context.getComponent(ApplicationContext.class);
        ApplicationContext componentByName = (ApplicationContext) context.getComponent("applicationContext");
        Assert.assertNotNull(component);
        Assert.assertNotNull(componentByName);
    }

    @Test
    public void testCustomResolvedDependencies() {
        ResolvedObject resolvedObject = new ResolvedObject();
        context.addResolvedDependency(resolvedObject);
        ResolvedObject resolved = context.getComponent(ResolvedObject.class);
        Assert.assertNotNull(resolved);
        Assert.assertEquals(resolvedObject.initTime, resolved.initTime);
    }

    @Test
    public void testCustomResolvedWithName() {
        ResolvedObject resolvedObject = new ResolvedObject();
        String customResolvedName = "customResolvedName";
        context.addResolvedDependency(resolvedObject, customResolvedName);
        ResolvedObject resolved = (ResolvedObject) context.getComponent(customResolvedName);

        Assert.assertNotNull(resolved);
        Assert.assertEquals(resolvedObject.initTime, resolved.initTime);
    }

}
