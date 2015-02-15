package metadatarepo.core.version.deps.strategy.latest;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.NoVersion;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;

/**
 * @author Gregory Boissinot
 */
public class LatestOrForcedDependencyVersionStrategy implements DependencyVersionStrategy {

    private LatestVersionRepository latestVersionRepository;

    @Override
    public Version getVersion(BuildDependency fetchedBuildDependency, ModuleStatus requestedStatus) {
        Version version = fetchedBuildDependency.getVersion();
        if (version instanceof NoVersion) {
            return getLatestVersion(fetchedBuildDependency.getOrg(), fetchedBuildDependency.getName(), requestedStatus);
        }
        return version;
    }

    private Version getLatestVersion(String org, String name, ModuleStatus status) {
        return VersionFactory.get(latestVersionRepository.getLatestVersionValue(org, name, status.getValue()));
    }
}
