package cz.encircled.ioc.model.postprocess;

import cz.encircled.ioc.annotation.Component;

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
