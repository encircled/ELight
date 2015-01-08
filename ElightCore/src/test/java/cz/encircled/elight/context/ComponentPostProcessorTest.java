package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.model.postprocess.ComponentToProcess;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class ComponentPostProcessorTest {

    @Test
    public void basicPostProcessorTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model.postprocess").initialize();
        ComponentToProcess component = context.getComponent(ComponentToProcess.class);
        Assert.assertNotNull(component);
        Assert.assertTrue(component.preProcessFlag);
        Assert.assertTrue(component.postProcessFlag);
    }

}
