package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public interface ModuleStatus {

    void promote(ModuleMetaVersion moduleMetaVersion);

    void release(ModuleMetaVersion moduleMetaVersion);

    String getValue();

    boolean isDefault();
}
