package cz.encircled.elight.scanner;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ClasspathScannerTest {

    @Test
    public void scannerTest() {
        ApplicationContext context = new AnnotationApplicationContext(this.getClass().getPackage().getName()).initialize();
        Assert.assertTrue(context.containsComponent("wow"));
        Assert.assertTrue(context.containsComponent(ScannerTestComponent.class));
    }

}
