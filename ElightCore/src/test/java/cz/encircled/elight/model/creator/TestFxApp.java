package cz.encircled.elight.model.creator;

import cz.encircled.elight.core.context.AnnotationApplicationContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Assert;

/**
 * Created by Kisel on 2/25/2015.
 */
public class TestFxApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Thread(() -> {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("cz.encircled.elight.model.creator");
            applicationContext.initialize();
            FxCreatorTester component = applicationContext.getComponent(FxCreatorTester.class);
            Assert.assertTrue(component.isConstructorFxThread);
            Platform.exit();
        }).start();
    }

}
