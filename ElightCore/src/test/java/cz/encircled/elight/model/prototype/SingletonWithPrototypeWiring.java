package cz.encircled.elight.model.prototype;


import cz.encircled.elight.annotation.Component;
import cz.encircled.elight.annotation.Wired;

/**
 * Created by Encircled on 24-Dec-14.
 */
@Component
public class SingletonWithPrototypeWiring {

    @Wired
    public PrototypeComponentWithTime prototypeComponentWithTime;

}
