package cz.encircled.elight.scanner;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import org.junit.Test;

/**
 * Created by Work on 1/8/2015.
 */
public class JarScannerTest {

    @Test
    public void jarTest() {
        ApplicationContext context = new AnnotationApplicationContext("cz.encircled.eplayer").initialize();

    }

}
