package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.exception.NoFieldFoundForGetterException;
import org.junit.Test;

/**
 * Created by Kisel on 2/25/2015.
 */
public class NoFieldFoundForGetterTest extends AbstractErrorTest {

    @Test(expected = NoFieldFoundForGetterException.class)
    public void ambiguousWiringTest() {
        new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.nofield").initialize();
    }

}
