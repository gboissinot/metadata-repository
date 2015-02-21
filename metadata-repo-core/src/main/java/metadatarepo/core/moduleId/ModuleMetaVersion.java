package metadatarepo.core.moduleId;

import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 */
public class ModuleMetaVersion {

    private final ModuleStatusNotifier moduleStatusNotifier = new ModuleStatusNotifier() {
        @Override
        public void changeStatus(ModuleStatus moduleStatus) {
            ModuleMetaVersion.this.setStatus(moduleStatus);
        }
    };

    private final Version version;

    private ModuleStatus status;

    public ModuleMetaVersion(Version version, ModuleStatus status) {
        if (version == null) {
            throw new IllegalArgumentException("A version object is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("A status object is required.");
        }
        this.version = version;
        this.status = status;
    }

    public boolean isStatusDefined() {
        return !(status instanceof NoModuleStatus);
    }

    /**
     * Gets the module version
     *
     * @return the @Version object. Never null.
     */
    public Version getVersion() {
        return version;
    }

    public ModuleStatus getStatus() {
        return status;
    }

    private void setStatus(ModuleStatus status) {
        this.status = status;
    }

    public void promote() {
        status.promote(moduleStatusNotifier);
    }

    public void release() {
        status.release(moduleStatusNotifier);
    }

}
