package cz.encircled.elight.errormodel.contexterror.mapgeneric;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

import java.util.Map;

/**
 * Created by Kisel on 2/25/2015.
 */
@Component
public class MapGenericTester {

    @Wired
    Map<String, Long> map;

}
