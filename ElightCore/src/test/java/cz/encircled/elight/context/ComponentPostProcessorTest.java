package cz.encircled.elight.context;

import cz.encircled.elight.model.postprocess.ComponentToProcess;
import cz.encircled.elight.model.postprocess.PrototypeComponentToProcess;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 24-Dec-14.
 */
public class ComponentPostProcessorTest extends AbstractContextTest {

    public ComponentPostProcessorTest() {
        super("cz.encircled.elight.model.postprocess");
    }

    @Test
    public void basicPostProcessorTest() {
        ComponentToProcess component = applicationContext.getComponent(ComponentToProcess.class);

        Assert.assertNotNull(component);
        Assert.assertTrue(component.preProcessFlag);
        Assert.assertTrue(component.postProcessFlag);
    }

    @Test
    public void prototypePostProcessorTest() {
        PrototypeComponentToProcess component = applicationContext.getComponent(PrototypeComponentToProcess.class);

        Assert.assertNotNull(component);
        Assert.assertTrue(component.preProcessFlag);
        Assert.assertTrue(component.postProcessFlag);
    }

}
