package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
public interface POMParentVersion {

    /**
     * Gets the Maven parent version.
     *
     * @return the parent version value. Cannot be null.
     */
    String getValue();
}
