package cz.encircled.elight.model.provider;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;
import cz.encircled.elight.model.house.Door;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.postprocess.PrototypeComponentToProcess;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;

/**
 * Created by Kisel on 1/13/2015.
 */
@Component
public class TestComponentWithProviders {

    @Wired
    private Provider<List<Door>> doorsProvider;

    @Wired
    private Provider<House> singletonProvider;

    @Inject
    private Provider<House> jsr330SingletonProvider;

    @Wired
    private Provider<PrototypeComponentToProcess> prototypeProvider;

    @Inject
    private Provider<PrototypeComponentToProcess> jsr330PrototypeProvider;

    public Provider<House> getSingletonProvider() {
        return singletonProvider;
    }

    public Provider<House> getJsr330SingletonProvider() {
        return jsr330SingletonProvider;
    }

    public Provider<PrototypeComponentToProcess> getPrototypeProvider() {
        return prototypeProvider;
    }

    public Provider<PrototypeComponentToProcess> getJsr330PrototypeProvider() {
        return jsr330PrototypeProvider;
    }

}
