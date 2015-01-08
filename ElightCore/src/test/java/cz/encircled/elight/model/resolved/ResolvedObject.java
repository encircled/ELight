package cz.encircled.elight.model.resolved;

/**
 * Created by Work on 1/8/2015.
 */
public class ResolvedObject implements ResolvedIdentification {

    public long initTime;

    public ResolvedObject() {
        initTime = System.nanoTime();
    }

}
