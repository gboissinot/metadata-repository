package metadatarepo.core.moduleId;

import java.util.ServiceLoader;

/**
 * @author Gregory Boissinot
 */
class ModuleStatusFactory {

    /**
     * Gets the module status from a label
     *
     * @param statusLabel the given label. Can be null if there no current status extracted by clients.
     * @return the module status object.
     */
    public static ModuleStatus get(String statusLabel) {

        if (statusLabel == null) {
            return getDefaultModuleStatus();
        }

        ServiceLoader<ModuleStatus> serviceLoader = ServiceLoader.load(ModuleStatus.class, Thread.currentThread().getContextClassLoader());
        serviceLoader.reload();
        for (ModuleStatus moduleStatus : serviceLoader) {
            if (statusLabel.equals(moduleStatus.getValue())) {
                return moduleStatus;
            }
        }

        throw new IllegalArgumentException(String.format("No status object found for %s.", statusLabel));
    }

    //TODO PUT ECHACHE SPRING
    //CACHE
    private static ModuleStatus getDefaultModuleStatus() {
        ServiceLoader<ModuleStatus> serviceLoader = ServiceLoader.load(ModuleStatus.class, Thread.currentThread().getContextClassLoader());
        serviceLoader.reload();
        for (ModuleStatus moduleStatus : serviceLoader) {
            if (moduleStatus.isDefault()) {
                return moduleStatus;
            }
        }
        throw new IllegalStateException("A status must be the default.");
    }
}
