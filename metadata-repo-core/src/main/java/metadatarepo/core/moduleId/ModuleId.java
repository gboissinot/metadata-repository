package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public interface ModuleId {

    String getOrg();

    String getName();

    ModuleMetaVersion getMetaVersion();
}
