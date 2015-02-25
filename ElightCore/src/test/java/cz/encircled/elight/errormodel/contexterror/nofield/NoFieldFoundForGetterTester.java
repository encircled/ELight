package cz.encircled.elight.errormodel.contexterror.nofield;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

/**
 * Created by Kisel on 2/25/2015.
 */
@Component
public class NoFieldFoundForGetterTester {

    @Wired
    public Object getMissingField() {
        return null;
    }

}
