package cz.encircled.elight.error;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import org.junit.Before;

/**
 * Created by Encircled on 11-Jan-15.
 */
public abstract class AbstractErrorTest {

    protected String packageToScan;

    protected ApplicationContext applicationContext;

    public AbstractErrorTest() {
        this.packageToScan = "cz.encircled.elight.model.errormodel.other";
    }

    public AbstractErrorTest(String packageToScan) {
        this.packageToScan = packageToScan;
    }

    @Before
    public void init() {
        applicationContext = new AnnotationApplicationContext(packageToScan).initialize();
    }

}
