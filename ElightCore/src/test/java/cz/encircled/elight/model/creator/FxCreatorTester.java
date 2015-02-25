package cz.encircled.elight.model.creator;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Creator;
import cz.encircled.elight.core.annotation.Scope;
import cz.encircled.elight.core.creator.FxInstanceCreator;
import javafx.application.Platform;

import javax.annotation.PostConstruct;

/**
 * Created by Kisel on 2/25/2015.
 */
@Component
@Creator(FxInstanceCreator.class)
@Scope(Scope.PROTOTYPE)
public class FxCreatorTester {

    public boolean isConstructorFxThread = false;

    public FxCreatorTester() {
        isConstructorFxThread = Platform.isFxApplicationThread();
    }

}
