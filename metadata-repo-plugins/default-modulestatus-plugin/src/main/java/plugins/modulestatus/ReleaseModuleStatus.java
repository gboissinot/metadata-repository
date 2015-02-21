package plugins.modulestatus;

import metadatarepo.core.moduleId.ModuleStatusNotifier;

/**
 * @author Gregory Boissinot
 */
public class ReleaseModuleStatus extends AbstractModuleStatus {
    public static final String VALUE = "RELEASE";

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
        return true;
    }
}
