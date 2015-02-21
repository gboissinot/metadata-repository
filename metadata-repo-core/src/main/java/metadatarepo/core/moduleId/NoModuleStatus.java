package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public class NoModuleStatus implements ModuleStatus {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
