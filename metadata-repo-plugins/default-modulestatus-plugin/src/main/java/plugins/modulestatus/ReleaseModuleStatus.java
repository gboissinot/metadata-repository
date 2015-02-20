package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleMetaVersion;

/**
 * @author Gregory Boissinot
 */
public class ReleaseModuleStatus extends AbstractModuleStatus {
    public static final String VALUE = "RELEASE";

    @Override
    public void promote(ModuleMetaVersion moduleMetaVersion) {
    }

    @Override
    public void release(ModuleMetaVersion moduleMetaVersion) {
    }

    @Override
    public String getValue() {
        return VALUE;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}
