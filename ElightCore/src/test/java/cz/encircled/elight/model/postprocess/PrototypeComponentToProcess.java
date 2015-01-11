package cz.encircled.elight.model.postprocess;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Component
@Scope(Scope.PROTOTYPE)
public class PrototypeComponentToProcess {

    public boolean preProcessFlag = false;

    public boolean postProcessFlag = false;

    @PostConstruct
    public void init() {
        postProcessFlag = false;
    }

}
