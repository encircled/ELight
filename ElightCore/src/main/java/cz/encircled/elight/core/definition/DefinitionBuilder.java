package cz.encircled.elight.core.definition;

/**
 * Created by Encircled on 10-Jan-15.
 */
public interface DefinitionBuilder {

    public static int DEFAULT_COMPONENT_ORDER = 0;

    /**
     * @param clazz - candidate class
     * @return <b>true</b> - if assigned <code>class</code> have to be managed by context
     */
    boolean checkCandidate(Class<?> clazz);

    String getName(Class<?> clazz);

    boolean isPostProcessor(Class<?> clazz);

    /**
     * @param clazz - candidate class
     * @return <b>true</b> - if candidate has no condition, or it has passed the condition
     */
    boolean isConditionTrue(Class<?> clazz);

}
