package cz.encircled.elight.model.prototype;


import cz.encircled.elight.annotation.Component;
import cz.encircled.elight.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 24-Dec-14.
 */
@Component
@Scope(Scope.PROTOTYPE)
public class PrototypeComponentWithTime {

    public Long initTime;

    @PostConstruct
    public void init() {
        initTime = System.nanoTime();
    }

}
