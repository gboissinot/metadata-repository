package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public class NoModuleStatus implements ModuleStatus {

    @Override
    public void promote() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }
}
