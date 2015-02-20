package metadatarepo.core.moduleId;

import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 */
public class ModuleMetaVersion {

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
     * Return the module version
     *
     * @return the @Version object. Never null.
     */
    public Version getVersion() {
        return version;
    }

    public ModuleStatus getStatus() {
        return status;
    }

    public void setStatus(ModuleStatus status) {
        this.status = status;
    }

    public void promote() {
        status.promote(this);
    }

    public void release() {
        status.release(this);
    }
}
