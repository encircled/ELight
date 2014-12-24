package cz.encircled.elight.core;

/**
 * Created by Encircled on 24-Dec-14.
 */
public interface ComponentPostProcessor {

    Object preProcess(Object component);

    Object postProcess(Object component);

}
