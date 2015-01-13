package cz.encircled.elight.context;

import cz.encircled.elight.model.provider.TestComponentWithProviders;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Provider;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kisel on 1/13/2015.
 */
public class DependencyProviderTest extends AbstractContextTest {

    @Test
    public void basicProviderTest() {
        TestComponentWithProviders component = applicationContext.getComponent(TestComponentWithProviders.class);
        doProviderTest(component.getPrototypeProvider(), false);
        doProviderTest(component.getJsr330PrototypeProvider(), false);
        doProviderTest(component.getSingletonProvider(), true);
        doProviderTest(component.getJsr330SingletonProvider(), true);
    }

    private void doProviderTest(Provider<?> provider, boolean singleton) {
        int runs = 10;
        Set<Object> set = new HashSet<>();
        for(int i = 0; i < runs; i++) {
            Object component = provider.get();
            Assert.assertNotNull(component);
            set.add(component);
        }
        Assert.assertEquals(singleton ? 1 : runs, set.size());
    }

}
