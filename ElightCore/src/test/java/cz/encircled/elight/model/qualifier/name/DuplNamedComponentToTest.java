package cz.encircled.elight.model.qualifier.name;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Encircled on 11-Jan-15.
 */
@Named("anotherCustomComponentName")
@Singleton
public class DuplNamedComponentToTest implements TestNamedInterface {

}
