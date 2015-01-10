package cz.encircled.elight.core;

import cz.encircled.elight.core.definition.DefinitionBuilder;
import cz.encircled.elight.core.exception.RuntimeELightException;
import cz.encircled.elight.core.factory.ComponentFactory;
import cz.encircled.elight.core.util.IOUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by encircled on 9/19/14.
 */
public class ClasspathResourcesScanner {

    private static final Logger log = LogManager.getLogger();

    public static final String DOT = "\\.";

    private static final String JAR = "jar";

    private static final String ZIP = "zip";

    private ComponentFactory componentFactory;

    private DefinitionBuilder definitionBuilder;

    public ClasspathResourcesScanner(ComponentFactory componentFactory, DefinitionBuilder definitionBuilder) {
        this.componentFactory = componentFactory;
        this.definitionBuilder = definitionBuilder;
    }

    /**
     * Look-up for components in all packages starting from <code>rootPackage</code>
     */
    public List<Class<?>> findComponentClasses(String rootPackage) {
        List<Class<?>> componentClasses;
        try {
            componentClasses = getComponentClassesFromPackage(rootPackage);
        } catch (Exception e) {
            throw new RuntimeELightException("Component classes search failed", e);
        }
        return componentClasses;
    }

    private List<Class<?>> getComponentClassesFromPackage(String rootPackage)
            throws IOException, ClassNotFoundException {
        if (StringUtils.isEmpty(rootPackage)) {
            throw new IllegalArgumentException("Root package must be specified");
        }
        log.debug("Classpath resources scan start, root package is {}", rootPackage);
        rootPackage = rootPackage.replace(".", "/");
        Enumeration<URL> root = Thread.currentThread().getContextClassLoader()
                .getResources(rootPackage);
        List<Class<?>> result = new ArrayList<>();
        while (root.hasMoreElements()) {
            URL url = root.nextElement();
            log.debug("Next element - {}", url);
            if (isJar(url.getProtocol())) {
                getComponentsFromJar(rootPackage, url, result);
            } else {
                List<File> filesInFolder = IOUtil.getFilesInFolder(url.getFile(), ".*\\.class");
                for (File f : filesInFolder) {
                    String className = f.getPath().substring(url.getFile().length()
                            - rootPackage.length() - 1).replaceAll("\\\\", ".");
                    className = IOUtil.getFileNameWithoutType(className);
                    Class candidateClass = Class.forName(className);
                    if (definitionBuilder.isPostProcessor(candidateClass)) {
                        componentFactory.registerPostProcessor(candidateClass);
                    }
                    if (definitionBuilder.checkCandidate(candidateClass)) {
                        log.debug("New resource annotated class {}", candidateClass.getName());
                        result.add(candidateClass);
                    }
                }
            }
        }
        return result;
    }

    private void getComponentsFromJar(String rootPackage, URL url, List<Class<?>> result) throws IOException, ClassNotFoundException {
        log.debug("Get components from JAR {}", url);
        URLConnection con = url.openConnection();
        JarFile jarFile;

        if (con instanceof JarURLConnection) {
            JarURLConnection jarCon = (JarURLConnection) con;
            jarFile = jarCon.getJarFile();
        } else {
            throw new RuntimeException("Not supported yet");
        }
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (jarEntry.getName().startsWith(rootPackage) && jarEntry.getName().endsWith(".class")) {
                String className = jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().length() - 6);
                Class candidateClass = Class.forName(className);
                if (definitionBuilder.isPostProcessor(candidateClass)) {
                    componentFactory.registerPostProcessor(candidateClass);
                }
                if (definitionBuilder.checkCandidate(candidateClass)) {
                    log.debug("New component annotated class {}", candidateClass.getName());
                    result.add(candidateClass);
                }
            }
        }
    }

    private boolean isJar(String protocol) {
        return JAR.equals(protocol) || ZIP.equals(protocol);
    }

}
