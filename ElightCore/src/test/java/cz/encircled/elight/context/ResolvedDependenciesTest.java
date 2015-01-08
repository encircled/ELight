package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.resolved.CollectionOfResolved;
import cz.encircled.elight.model.resolved.ResolvedObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Work on 1/8/2015.
 */
public class ResolvedDependenciesTest {

    private static ApplicationContext context;

    @BeforeClass
    public static void setupContext() {
        context = new AnnotationApplicationContext("cz.encircled.elight").initialize();
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

    @Test
    public void testAddResolvedBeforeInitialize() {
        ApplicationContext applicationContext = new AnnotationApplicationContext("cz.encircled.elight.model.resolved");
        applicationContext.addResolvedDependency(new ResolvedObject());
        applicationContext.initialize();
        Assert.assertNotNull(applicationContext.getComponent("resolvedObject"));
        Assert.assertNotNull(applicationContext.getComponent(ResolvedObject.class));
    }

    @Test
    public void testResolvedCollections() {
        ApplicationContext applicationContext = new AnnotationApplicationContext("cz.encircled.elight.model.resolved");
        applicationContext.addResolvedDependency(new ResolvedObject());
        applicationContext.initialize();

        CollectionOfResolved withoutResolved = context.getComponent(CollectionOfResolved.class);
        Assert.assertTrue(withoutResolved.resolvedObjectsArray.length == 1);
        Assert.assertTrue(withoutResolved.resolvedObjectsList.size() == 1);
        Assert.assertTrue(withoutResolved.resolvedObjectsMap.size() == 1);

        CollectionOfResolved withResolved = applicationContext.getComponent(CollectionOfResolved.class);
        Assert.assertTrue(withResolved.resolvedObjectsArray.length == 2);
        Assert.assertTrue(withResolved.resolvedObjectsList.size() == 2);
        Assert.assertTrue(withResolved.resolvedObjectsMap.size() == 2);
    }

}
