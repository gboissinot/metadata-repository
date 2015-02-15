package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
class NoModuleStatus implements ModuleStatus {

    @Override
    public void promote() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }
}
