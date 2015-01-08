package cz.encircled.elight.model.house;


import cz.encircled.elight.core.annotation.Wired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Encircled on 22-Dec-14.
 */
public class AbstractHouse {

    @Wired
    private Door door;

    protected boolean initCalled = false;

    protected boolean destroyCalled = false;

    @PostConstruct
    private void init() {
        initCalled = true;
    }

    @PreDestroy
    private void destroy() {
        destroyCalled = true;
    }

    public Door getDoor() {
        return door;
    }
}
