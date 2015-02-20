package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleMetaVersion;
import metadatarepo.core.moduleId.ModuleStatus;

/**
 * @author Gregory Boissinot
 */
public abstract class AbstractModuleStatus implements ModuleStatus {

    public void deprecate(ModuleMetaVersion moduleMetaVersion) {
        moduleMetaVersion.setStatus(new DeprecateModuleStatus());
    }
}
