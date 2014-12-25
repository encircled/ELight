package cz.encircled.elight.model.postprocess;


import cz.encircled.elight.core.ComponentPostProcessor;
import cz.encircled.elight.core.annotation.Component;

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
