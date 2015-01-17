package cz.encircled.elight.errormodel.contexterror.ambiguous;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Component
public class AmbiguousTestComponent {

    @Wired
    private InterfaceToDuplicate interfaceToDuplicate;

}
