package cz.encircled.elight.core.creator;

import cz.encircled.elight.util.ReflectionUtil;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Encircled on 16/09/2014.
 */
public class FxInstanceCreator implements InstanceCreator {

    private Logger log = LogManager.getLogger();

    @Override
    public <T> T createInstance(Class<T> clazz) {
        return Platform.isFxApplicationThread() ? ReflectionUtil.instance(clazz) : createInFxThread(clazz);
    }

    private <T> T createInFxThread(Class<T> clazz) {
        final Object[] instance = {null};
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                instance[0] = ReflectionUtil.instance(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.debug("{} created in FX creator and FX thread", clazz);
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e);
        }
        return (T) instance[0];
    }

}
