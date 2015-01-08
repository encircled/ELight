package cz.encircled.elight.core.context;

import cz.encircled.elight.core.ClasspathResourcesScanner;
import cz.encircled.elight.core.definition.AnnotationDefinitionBuilder;
import cz.encircled.elight.core.factory.DefaultComponentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * Created by Encircled on 11/09/2014.
 */
public class AnnotationApplicationContext extends AbstractApplicationContext implements ApplicationContext {

    private static final Logger log = LogManager.getLogger();

    public AnnotationApplicationContext(String rootPackage) {
        log.debug("Annotation context start");
        componentFactory = new DefaultComponentFactory();
        definitionBuilder = new AnnotationDefinitionBuilder();

        List<Class<?>> componentClasses = new ClasspathResourcesScanner(componentFactory).findComponentClasses(rootPackage);
        componentClasses.parallelStream().forEach(c -> {
            componentFactory.registerDefinition(definitionBuilder.buildDefinition(c));
        });
        startContext();
    }

}
