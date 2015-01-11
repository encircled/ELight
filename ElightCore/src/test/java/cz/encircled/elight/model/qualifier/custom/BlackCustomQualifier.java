package cz.encircled.elight.model.qualifier.custom;

import javax.inject.Singleton;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Singleton
@TestCustomQualifier(color = TestCustomQualifier.Color.BLACK)
public class BlackCustomQualifier implements CustomQualifierInterface {
}
