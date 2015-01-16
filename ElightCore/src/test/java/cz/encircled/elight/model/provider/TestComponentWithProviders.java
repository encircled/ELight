package cz.encircled.elight.model.provider;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.house.Window;
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
    private Provider<List<Window>> windowsProvider;

    @Wired
    private Provider<House> singletonProvider;

    @Inject
    private Provider<House> jsr330SingletonProvider;

    @Wired
    private Provider<PrototypeComponentToProcess> prototypeProvider;

    @Inject
    private Provider<PrototypeComponentToProcess> jsr330PrototypeProvider;

    private Provider<List<Window>> methodWindowsProvider;

    private Provider<House> jsr330MethodSingletonProvider;

    private Provider<PrototypeComponentToProcess> methodPrototypeProvider;



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

    public Provider<PrototypeComponentToProcess> getMethodPrototypeProvider() {
        return methodPrototypeProvider;
    }

    public Provider<List<Window>> getWindowsProvider() {
        return windowsProvider;
    }

    @Wired
    public void setMethodPrototypeProvider(Provider<PrototypeComponentToProcess> methodPrototypeProvider) {
        this.methodPrototypeProvider = methodPrototypeProvider;
    }

    public Provider<House> getJsr330MethodSingletonProvider() {
        return jsr330MethodSingletonProvider;
    }

    @Inject
    public void setJsr330MethodSingletonProvider(Provider<House> jsr330MethodSingletonProvider) {
        this.jsr330MethodSingletonProvider = jsr330MethodSingletonProvider;
    }

    public Provider<List<Window>> getMethodWindowsProvider() {
        return methodWindowsProvider;
    }

    @Wired
    public void setMethodWindowsProvider(Provider<List<Window>> methodWindowsProvider) {
        this.methodWindowsProvider = methodWindowsProvider;
    }

}
