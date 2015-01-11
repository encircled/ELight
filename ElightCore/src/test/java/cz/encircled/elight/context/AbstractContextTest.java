package cz.encircled.elight.context;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import cz.encircled.elight.core.context.ApplicationContext;
import org.junit.Before;

/**
 * Created by Encircled on 11-Jan-15.
 */
public abstract class AbstractContextTest {

    protected String packageToScan;

    protected ApplicationContext applicationContext;

    public AbstractContextTest() {
        packageToScan = "cz.encircled.elight.model";
    }

    public AbstractContextTest(String packageToScan) {
        this.packageToScan = packageToScan;
    }

    @Before
    public void init() {
        applicationContext = new AnnotationApplicationContext(packageToScan).initialize();
    }

}
