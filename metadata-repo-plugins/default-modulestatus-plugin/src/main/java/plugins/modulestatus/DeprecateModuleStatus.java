package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleStatusNotifier;

/**
 * @author Gregory Boissinot
 */
public class DeprecateModuleStatus extends AbstractModuleStatus {
    public static final String VALUE = "DEPRECATE";

    @Override
    public void promote(ModuleStatusNotifier moduleStatusNotifier) {
        //DO NOTHING
    }

    @Override
    public void release(ModuleStatusNotifier moduleStatusNotifier) {
        //DO NOTHING
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
