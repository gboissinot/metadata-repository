package plugins.maven;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleMetaVersion;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;

/**
 * @author Gregory Boissinot
 */
class POMVersionEngine {

    private static String VERSION_STATUS_SEPERATOR = ".";

    public Version buildMavenPOMVersion(ModuleId moduleId) {
        ModuleMetaVersion metaVersion = moduleId.getMetaVersion();
        Version version = metaVersion.getVersion();
        if (metaVersion.isStatusDefined()) {
            return contactVersionWithStatus(version, metaVersion.getStatus());
        } else {
            return version;
        }
    }

    private Version contactVersionWithStatus(Version version, ModuleStatus status) {
        assert version != null;
        assert status != null;
        return VersionFactory.get(version.getValue() + VERSION_STATUS_SEPERATOR + status.getValue());
    }
}
