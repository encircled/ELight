package cz.encircled.elight.model.required;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;
import cz.encircled.elight.model.house.House;
import cz.encircled.elight.model.resolved.ResolvedObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Kisel on 1/9/2015.
 */
@Component
public class ComponentRequiredObjectTest {

    @Wired(required = false, name = "notExistingHouse")
    public House house;

    @Wired(required = false)
    public ResolvedObject resolvedObject;

    @Wired(required = false)
    public Map<String, ResolvedObject> resolvedObjectsMap;

    @Wired(required = false)
    public ResolvedObject[] resolvedObjectArray;

    @Wired(required = false)
    public List<ResolvedObject> resolvedObjectList;

}
