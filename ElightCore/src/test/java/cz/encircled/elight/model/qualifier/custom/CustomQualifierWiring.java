package cz.encircled.elight.model.qualifier.custom;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

import javax.inject.Inject;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Component
public class CustomQualifierWiring {

    @Wired
    @TestCustomQualifier(color = TestCustomQualifier.Color.RED)
    private CustomQualifierInterface red;

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
    private CustomQualifierInterface black;

    @Inject
    @TestCustomQualifier
    private CustomQualifierInterface white;

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
    @AnotherTestCustomQualifier(anotherColor = AnotherTestCustomQualifier.AnotherColor.WHITE)
    private CustomQualifierInterface blackAndWhite;

    private CustomQualifierInterface methodBlackAndWhite;

    private CustomQualifierInterface getterBlackAndWhite;

    public CustomQualifierInterface getRed() {
        return red;
    }

    public CustomQualifierInterface getBlack() {
        return black;
    }

    public CustomQualifierInterface getWhite() {
        return white;
    }

    public CustomQualifierInterface getBlackAndWhite() {
        return blackAndWhite;
    }

    public CustomQualifierInterface getMethodBlackAndWhite() {
        return methodBlackAndWhite;
    }

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
    @AnotherTestCustomQualifier(anotherColor = AnotherTestCustomQualifier.AnotherColor.WHITE)
    public void setMethodBlackAndWhite(CustomQualifierInterface methodBlackAndWhite) {
        this.methodBlackAndWhite = methodBlackAndWhite;
    }

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
    @AnotherTestCustomQualifier(anotherColor = AnotherTestCustomQualifier.AnotherColor.WHITE)
    public CustomQualifierInterface getGetterBlackAndWhite() {
        return getterBlackAndWhite;
    }

}
