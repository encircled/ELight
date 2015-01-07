package cz.encircled.elight.model.creator;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Creator;

/**
 * Created by Work on 1/7/2015.
 */
@Component
@Creator(TestCreator.class)
public class CreationTester {

    public boolean createTestFlag = false;

}
