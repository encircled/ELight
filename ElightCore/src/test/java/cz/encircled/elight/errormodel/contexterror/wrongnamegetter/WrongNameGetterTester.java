package cz.encircled.elight.errormodel.contexterror.wrongnamegetter;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;
import cz.encircled.elight.error.AbstractErrorTest;

/**
 * Created by Kisel on 2/25/2015.
 */
@Component
public class WrongNameGetterTester extends AbstractErrorTest {

    @Wired
    public Object notAGetter() {
        return null;
    }

}
