package cz.encircled.ioc.model.prototype;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.annotation.Wired;

/**
 * Created by Encircled on 24-Dec-14.
 */
@Component
public class SingletonWithPrototypeWiring {

    @Wired
    public PrototypeComponentWithTime prototypeComponentWithTime;

}
