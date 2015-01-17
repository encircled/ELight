package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import cz.encircled.elight.core.exception.AmbiguousDependencyException;
import org.junit.Test;

/**
 * Created by Encircled on 11-Jan-15.
 */
public class AmbiguousWiringTest extends AbstractErrorTest {

    @Test(expected = AmbiguousDependencyException.class)
    public void ambiguousWiringTest() {
        ApplicationContext innerContext = new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.ambiguous").initialize();
    }

}
