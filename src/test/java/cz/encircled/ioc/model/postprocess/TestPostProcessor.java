package cz.encircled.ioc.model.postprocess;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.core.ComponentPostProcessor;

/**
 * Created by Encircled on 24-Dec-14.
 */
@Component
public class TestPostProcessor implements ComponentPostProcessor {

    @Override
    public Object preProcess(Object component) {
        if (component instanceof ComponentToProcess) {
            ((ComponentToProcess) component).preProcessFlag = true;
        }
        return component;
    }

    @Override
    public Object postProcess(Object component) {
        if (component instanceof ComponentToProcess) {
            ((ComponentToProcess) component).postProcessFlag = true;
        }
        return component;
    }

}
