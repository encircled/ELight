package cz.encircled.elight.core.context;

import cz.encircled.elight.core.definition.AbstractDefinitionBuilder;
import cz.encircled.elight.core.definition.AnnotationDefinitionBuilder;
import cz.encircled.elight.core.factory.ComponentFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by encircled on 9/19/14.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private static final Logger log = LogManager.getLogger();

    protected AbstractDefinitionBuilder definitionBuilder;

    protected ComponentFactory componentFactory;

    private volatile boolean isDestroyed;

    public AbstractApplicationContext() {
        definitionBuilder = new AnnotationDefinitionBuilder();
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        return componentFactory.getComponentOfType(clazz);
    }

    @Override
    public Object getComponent(String name) {
        return componentFactory.getComponent(name);
    }

    @Override
    public boolean containsComponent(String name) {
        return componentFactory.containsComponent(name);
    }

    @Override
    public boolean containsComponent(Class<?> clazz) {
        return componentFactory.containsComponent(clazz);
    }

    @Override
    public void addResolvedDependency(Object component, String name) {
        if (StringUtils.isEmpty(name)) {
            name = definitionBuilder.getName(component.getClass());
        }
        componentFactory.addResolvedDependency(component, name);
    }

    @Override
    public void addResolvedDependency(Object component) {
        addResolvedDependency(component, null);
    }

    @Override
    public void destroy() {
        log.debug("Context destroy attempt");
        if(isDestroyed) {
            log.debug("Context is already destroyed");
        }
        isDestroyed = true;
        componentFactory.onDestroy();
    }

    public void startContext() {
        addResolvedDependency(this, "applicationContext");
        componentFactory.instantiateSingletons();
    }

}
