package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.exception.SelfReferenceOnPrototypeException;
import cz.encircled.elight.errormodel.contexterror.selfref.PrototypeSelfRefComponent;
import cz.encircled.elight.model.house.House;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kisel on 1/17/2015.
 */
public class ComponentSelfReferenceTest extends AbstractContextTest {

    @Test
    public void singletonSelfReferenceTest() {
        House component = applicationContext.getComponent(House.class);

        Assert.assertNotNull(component);
        Assert.assertNotNull(component.self);
        Assert.assertEquals(component, component.self);
        Assert.assertEquals(component.self, component.self.self);
    }

    @Test(expected = SelfReferenceOnPrototypeException.class)
    public void prototypeSelfReferenceTest() {
        new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.selfref")
                .initialize()
                .getComponent(PrototypeSelfRefComponent.class);
    }

}
