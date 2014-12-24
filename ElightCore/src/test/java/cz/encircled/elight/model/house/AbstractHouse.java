package cz.encircled.elight.model.house;


import cz.encircled.elight.annotation.Wired;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class AbstractHouse {

    @Wired
    private Door door;

    protected boolean initCalled;

    @PostConstruct
    private void init() {
        initCalled = true;
        System.out.println("I'm abstract house init");
    }

    public Door getDoor() {
        return door;
    }
}
