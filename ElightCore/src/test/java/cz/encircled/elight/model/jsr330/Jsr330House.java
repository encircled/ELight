package cz.encircled.elight.model.jsr330;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Encircled on 10-Jan-15.
 */
@Singleton
public class Jsr330House {

    @Inject
    private Jsr330Room jsr330Room;

    public Jsr330Room getJsr330Room() {
        return jsr330Room;
    }

}
