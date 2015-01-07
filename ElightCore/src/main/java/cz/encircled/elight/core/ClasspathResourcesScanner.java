package cz.encircled.elight.core;

import cz.encircled.elight.core.annotation.Component;
import cz.encircled.elight.core.annotation.Conditional;
import cz.encircled.elight.core.exception.RuntimeELightException;
import cz.encircled.elight.core.factory.ComponentFactory;
import cz.encircled.elight.core.util.IOUtil;
import cz.encircled.elight.core.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * Created by encircled on 9/19/14.
 */
public class ClasspathResourcesScanner {

    private static final Logger log = LogManager.getLogger();

    private static final Pattern CLASS_PATTERN = Pattern.compile("^[^\\$]+\\.class$");

    private static final FileFilter FILE_FILTER = pathname -> pathname.isDirectory() || CLASS_PATTERN.matcher(pathname.getName()).matches();

    public static final String DOT = "\\.";

    public static final String CLASS_SEPARATOR = "/";

    private static final String JAR = "jar";

    private static final String ZIP = "zip";

    private ComponentFactory componentFactory;

    public ClasspathResourcesScanner(ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
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
                    if (isPostProcessor(candidateClass)) {
                        componentFactory.registerPostProcessor(candidateClass);
                    }
                    if (checkCandidate(candidateClass)) {
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
                if (isPostProcessor(candidateClass)) {
                    componentFactory.registerPostProcessor(candidateClass);
                }
                if (checkCandidate(candidateClass)) {
                    log.debug("New component annotated class {}", candidateClass.getName());
                    result.add(candidateClass);
                }
            }
        }
    }

    private boolean isPostProcessor(Class candidateClass) {
        return ComponentPostProcessor.class.isAssignableFrom(candidateClass) && ReflectionUtil.isConcrete(candidateClass);
    }

    private boolean checkCandidate(Class<?> clazz) {
        return clazz.getAnnotation(Component.class) != null && !Modifier.isAbstract(clazz.getModifiers()) && checkCondition(clazz);
    }

    private boolean checkCondition(Class<?> clazz) {
        Conditional conditional = clazz.getAnnotation(Conditional.class);
        return conditional == null || ReflectionUtil.instance(conditional.value()).addToContext(clazz);
    }

    private boolean isJar(String protocol) {
        return JAR.equals(protocol) || ZIP.equals(protocol);
    }

}
