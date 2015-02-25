package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.exception.WiredMapGenericException;
import org.junit.Test;

/**
 * Created by Kisel on 2/25/2015.
 */
public class MapGenericErrorTest extends AbstractErrorTest {

    @Test(expected = WiredMapGenericException.class)
    public void ambiguousWiringTest() {
        new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.mapgeneric").initialize();
    }

}
