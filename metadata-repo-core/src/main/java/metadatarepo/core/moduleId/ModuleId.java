package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public interface ModuleId {

    /**
     * Gets the module organization
     *
     * @return the organization
     */
    String getOrg();

    /**
     * Get the module name
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the module meta version represented by a status and optionally a version
     *
     * @return the module meta version
     */
    ModuleMetaVersion getMetaVersion();

    /**
     * Promotes the metadata module to the next buildable status
     */
    void promote();

    /**
     * Promotes the metadata module to the end buildable state
     */
    void release();

    /**
     * Deprecates the metadata module
     */
    void deprecate();
}
