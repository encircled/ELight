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
// TODO forbidden dependencies
// TODO support for disabling JSR 330 for better performance (qualifiers only?)
public class AnnotationApplicationContext extends AbstractApplicationContext implements ApplicationContext {

    private static final Logger log = LogManager.getLogger();

    public String rootPackage;

    public AnnotationApplicationContext(String rootPackage) {
        log.debug("Annotation context create");
        this.rootPackage = rootPackage;
        componentFactory = new DefaultComponentFactory();
        definitionBuilder = new AnnotationDefinitionBuilder();
    }

    @Override
    public ApplicationContext initialize() {
        log.debug("Annotation context initializing...");

        List<Class<?>> componentClasses = new ClasspathResourcesScanner(componentFactory, definitionBuilder).findComponentClasses(rootPackage);
        componentClasses.parallelStream().unordered().forEach(c -> {
            componentFactory.registerDefinition(definitionBuilder.buildDefinition(c));
        });
        startContext();
        return this;
    }

}
