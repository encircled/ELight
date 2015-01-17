package cz.encircled.elight.errormodel.contexterror.selfref;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Scope;
import cz.encircled.elight.core.annotation.Wired;

/**
 * Created by Kisel on 1/17/2015.
 */
@Component
@Scope(Scope.PROTOTYPE)
public class PrototypeSelfRefComponent {

    @Wired
    public PrototypeSelfRefComponent self;

}
