package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleMetaVersion;

/**
 * @author Gregory Boissinot
 */
public class BuildModuleStatus extends AbstractModuleStatus {
    public static final String VALUE = "BUILD";

    @Override
    public void promote(ModuleMetaVersion moduleMetaVersion) {
        moduleMetaVersion.setStatus(new IntegrationModuleStatus());
    }

    @Override
    public void release(ModuleMetaVersion moduleMetaVersion) {
        moduleMetaVersion.setStatus(new ReleaseModuleStatus());
    }

    @Override
    public String getValue() {
        return VALUE;
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
