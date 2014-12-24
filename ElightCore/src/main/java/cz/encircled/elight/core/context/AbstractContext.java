package cz.encircled.elight.core.context;

import cz.encircled.elight.core.definition.AnnotationDefinitionBuilder;
import cz.encircled.elight.core.definition.DefinitionBuilder;
import cz.encircled.elight.core.exception.ComponentNotFoundException;
import cz.encircled.elight.core.factory.ComponentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by encircled on 9/19/14.
 */
public abstract class AbstractContext implements Context {

    private static final Logger log = LogManager.getLogger();

    protected DefinitionBuilder definitionBuilder;

    protected ComponentFactory componentFactory;

    public AbstractContext() {
        definitionBuilder = new AnnotationDefinitionBuilder();
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        return componentFactory.getComponent(clazz);
    }

    @Override
    public Object getComponent(String name) {
        return componentFactory.getComponent(name);
    }

    @Override
    public boolean containsComponent(String name) {
        try {
            getComponent(name);
            return true;
        } catch (ComponentNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean containsComponent(Class<?> clazz) {
        try {
            getComponent(clazz);
            return true;
        } catch (ComponentNotFoundException e) {
            return false;
        }
    }

    /**
     * Add singleton <code>component</code> to context
     *
     * @param component - component instance
     */
    @Override
    public void addComponent(Object component) {
//        componentFactory.addComponent();
    }

    public void startContext() {
        componentFactory.instantiateSingletons();
    }

}
