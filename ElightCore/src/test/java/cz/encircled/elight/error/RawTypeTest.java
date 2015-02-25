package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.exception.RawTypeException;
import org.junit.Test;

/**
 * Created by Kisel on 2/25/2015.
 */
public class RawTypeTest extends AbstractErrorTest {

    @Test(expected = RawTypeException.class)
    public void ambiguousWiringTest() {
        new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.rawtype").initialize();
    }

}
