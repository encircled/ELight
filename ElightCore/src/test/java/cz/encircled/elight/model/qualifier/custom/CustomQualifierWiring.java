package cz.encircled.elight.model.qualifier.custom;

import cz.encircled.elight.core.annotation.Component;

import javax.inject.Inject;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Component
public class CustomQualifierWiring {

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.RED)
    private CustomQualifierInterface red;

    @Inject
    @TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
    private CustomQualifierInterface black;

    @Inject
    @TestCustomQualifier
    private CustomQualifierInterface white;


    public CustomQualifierInterface getRed() {
        return red;
    }

    public CustomQualifierInterface getBlack() {
        return black;
    }

    public CustomQualifierInterface getWhite() {
        return white;
    }

}
