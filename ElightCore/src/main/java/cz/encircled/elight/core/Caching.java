package cz.encircled.elight.core;

/**
 * Created by Kisel on 1/23/2015.
 */
public interface Caching {

    void clearCache();

    boolean isCacheAllowed();

    void setCacheAllowed(boolean isAllowed);

}
