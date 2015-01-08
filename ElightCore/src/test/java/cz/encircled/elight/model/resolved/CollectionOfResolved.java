package cz.encircled.elight.model.resolved;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Wired;

import java.util.List;
import java.util.Map;

/**
 * Created by Work on 1/8/2015.
 */
@Component
public class CollectionOfResolved {

    @Wired
    public List<ResolvedIdentification> resolvedObjectsList;

    @Wired
    public ResolvedIdentification[] resolvedObjectsArray;

    @Wired
    public Map<ResolvedIdentification, Void> resolvedObjectsMap;

}
