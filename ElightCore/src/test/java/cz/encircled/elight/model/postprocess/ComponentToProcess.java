package cz.encircled.elight.model.postprocess;


import cz.encircled.elight.core.annotation.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Encircled on 24-Dec-14.
 */
@Component
public class ComponentToProcess {

    public boolean preProcessFlag = false;

    public boolean postProcessFlag = false;

    @PostConstruct
    public void init() {
        postProcessFlag = false;
    }

}
