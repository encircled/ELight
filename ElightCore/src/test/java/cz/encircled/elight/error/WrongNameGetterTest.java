package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.exception.WrongGetterException;
import org.junit.Test;

/**
 * Created by Kisel on 2/25/2015.
 */
public class WrongNameGetterTest extends AbstractErrorTest {

    @Test(expected = WrongGetterException.class)
    public void ambiguousWiringTest() {
        new AnnotationApplicationContext("cz.encircled.elight.errormodel.contexterror.wrongnamegetter").initialize();
    }

}
