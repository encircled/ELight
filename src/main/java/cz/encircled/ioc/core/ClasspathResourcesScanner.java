package cz.encircled.ioc.core;

import cz.encircled.ioc.annotation.Component;
import cz.encircled.ioc.annotation.Conditional;
import cz.encircled.ioc.exception.RuntimeELightException;
import cz.encircled.ioc.util.ReflectionUtil;
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
                int pathPrefixLength = url.getFile().length()
                        - rootPackage.length() - 1;
                File rootFile = new File(url.getFile());
                recursiveList(rootFile, pathPrefixLength, result);
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
            System.out.println("Next jar entry "
                    + jarEntry.getName());
            if (jarEntry.getName().startsWith(rootPackage) && jarEntry.getName().endsWith(".class")) {
                String className = jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().length() - 6);
                Class componentClass = Class.forName(className);
                if (checkCandidate(componentClass)) {
                    log.debug("New component annotated class {}", componentClass.getName());
                    result.add(componentClass);
                }
            }
        }
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

    private void recursiveList(File rootFile, int pathPrefixLength,
                               Collection<Class<?>> result) throws ClassNotFoundException {
        File[] files = rootFile.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    String className = rootFile
                            + "."
                            + f.getName()
                            .substring(0, f.getName().length() - 6);
                    className = className.substring(pathPrefixLength,
                            className.length());

                    if (className.startsWith(File.separator)) {
                        className = className.substring(1);
                    }

                    className = className.replace(File.separator, ".");

                    try {
                        Class<?> componentClass = Class.forName(className);
                        if (checkCandidate(componentClass)) {
                            log.debug("New resource annotated class {}", componentClass.getName());
                            result.add(componentClass);
                        }
                    } catch (ClassNotFoundException c) {
                        log.debug("Class not found " + className);
                    }


                } else {
                    recursiveList(f, pathPrefixLength, result);
                }
            }
        }
    }

}
