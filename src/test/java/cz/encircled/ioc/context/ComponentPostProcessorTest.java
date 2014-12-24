package cz.encircled.ioc.context;

import cz.encircled.ioc.core.context.AnnotationContext;
import cz.encircled.ioc.model.postprocess.ComponentToProcess;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class ComponentPostProcessorTest {

    @Test
    public void basicPostProcessorTest() {
        AnnotationContext context = new AnnotationContext("cz.encircled.ioc.model.postprocess");
        ComponentToProcess component = context.getComponent(ComponentToProcess.class);
        Assert.assertNotNull(component);
        Assert.assertTrue(component.preProcessFlag);
        Assert.assertTrue(component.postProcessFlag);
    }

}
