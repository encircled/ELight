package cz.encircled.elight.scanner;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ClasspathScannerTest {

    @Test
    public void scannerTest() {
        AnnotationApplicationContext context = new AnnotationApplicationContext(this.getClass().getPackage().getName());
        Assert.assertTrue(context.containsComponent("wow"));
        Assert.assertTrue(context.containsComponent(ScannerTestComponent.class));
    }

}
