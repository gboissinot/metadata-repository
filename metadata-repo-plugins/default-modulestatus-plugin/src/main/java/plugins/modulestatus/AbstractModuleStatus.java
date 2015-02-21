package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.moduleId.ModuleStatusNotifier;

/**
 * @author Gregory Boissinot
 */
public abstract class AbstractModuleStatus implements ModuleStatus {

    public static final ModuleStatus INTEGRATION = new IntegrationModuleStatus();
    public static final ModuleStatus RELEASE = new ReleaseModuleStatus();
    public static final ModuleStatus DEPREACATE = new DeprecateModuleStatus();

    public void deprecate(ModuleStatusNotifier moduleStatusNotifier) {
        moduleStatusNotifier.changeStatus(DEPREACATE);
    }
}
