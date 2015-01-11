package cz.encircled.elight.model.qualifier.name;

import cz.encircled.elight.core.annotation.Wired;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Singleton
public class WiredNameComponent {

    @Wired(name = "customComponentName")
    private TestNamedInterface namedComponentToTest;

    @Inject
    @Named("anotherCustomComponentName")
    private TestNamedInterface jsr330NamedComponentToTest;

    public TestNamedInterface getNamedComponentToTest() {
        return namedComponentToTest;
    }

    public TestNamedInterface getJsr330NamedComponentToTest() {
        return jsr330NamedComponentToTest;
    }

}
