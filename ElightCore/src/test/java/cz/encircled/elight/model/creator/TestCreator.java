package cz.encircled.elight.model.creator;

import cz.encircled.elight.core.creator.InstanceCreator;
import cz.encircled.elight.core.util.ReflectionUtil;

/**
 * Created by Work on 1/7/2015.
 */
public class TestCreator implements InstanceCreator {

    @Override
    public <T> T createInstance(Class<T> clazz) {
        T instance = ReflectionUtil.instance(clazz);
        if(instance instanceof CreationTester) {
            ((CreationTester) instance).createTestFlag = true;
        }
        return instance;
    }

}
