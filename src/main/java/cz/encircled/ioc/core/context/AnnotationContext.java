package cz.encircled.ioc.core.context;

import cz.encircled.ioc.core.ClasspathResourcesScanner;
import cz.encircled.ioc.core.definition.AnnotationDefinitionBuilder;
import cz.encircled.ioc.core.factory.DefaultComponentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * Created by Encircled on 11/09/2014.
 */
public class AnnotationContext extends AbstractContext implements Context {

    private static final Logger log = LogManager.getLogger();

    public AnnotationContext(String rootPackage) {
        log.debug("Annotation context start");
        componentFactory = new DefaultComponentFactory();
        definitionBuilder = new AnnotationDefinitionBuilder();

        List<Class<?>> componentClasses = new ClasspathResourcesScanner().findComponentClasses(rootPackage);
        componentClasses.stream().forEach(c -> {
            componentFactory.registerDefinition(definitionBuilder.buildDefinition(c));
        });
        startContext();
    }

}
