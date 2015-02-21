package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleStatusNotifier;

/**
 * @author Gregory Boissinot
 */
public class BuildModuleStatus extends AbstractModuleStatus {
    public static final String VALUE = "BUILD";

    @Override
    public void promote(ModuleStatusNotifier moduleStatusNotifier) {
        moduleStatusNotifier.changeStatus(AbstractModuleStatus.INTEGRATION);
    }

    @Override
    public void release(ModuleStatusNotifier moduleStatusNotifier) {
        moduleStatusNotifier.changeStatus(AbstractModuleStatus.RELEASE);
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
