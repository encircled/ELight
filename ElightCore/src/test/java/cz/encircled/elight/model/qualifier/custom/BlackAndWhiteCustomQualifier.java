package cz.encircled.elight.model.qualifier.custom;

import cz.encircled.elight.core.annotation.Component;

/**
 * Created by Kisel on 1/12/2015.
 */
@Component
@TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
@AnotherTestCustomQualifier(anotherColor = AnotherTestCustomQualifier.AnotherColor.WHITE)
public class BlackAndWhiteCustomQualifier implements CustomQualifierInterface {
}
