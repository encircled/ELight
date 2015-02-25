package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.core.exception.DuplicatedComponentException;
import cz.encircled.elight.model.house.House;
import org.junit.Test;

/**
 * Created by Kisel on 2/25/2015.
 */
public class DuplicatedComponentTest extends AbstractErrorTest {

    @Test(expected = DuplicatedComponentException.class)
    public void duplicatedResolvedComponentTest() {
        applicationContext.addResolvedDependency(this);
        applicationContext.addResolvedDependency(this);
    }

    @Test(expected = DuplicatedComponentException.class)
    public void duplicatedComponentTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.elight.model").initialize();
        context.addResolvedDependency(new House());
    }

}
