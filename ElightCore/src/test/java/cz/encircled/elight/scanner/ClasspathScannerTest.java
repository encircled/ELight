package cz.encircled.elight.scanner;

import cz.encircled.elight.core.context.AnnotationContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class ClasspathScannerTest {

    @Test
    public void scannerTest() {
        AnnotationContext context = new AnnotationContext(this.getClass().getPackage().getName());
        Assert.assertTrue(context.containsComponent("wow"));
        Assert.assertTrue(context.containsComponent(ScannerTestComponent.class));
    }

}
