package metadatarepo.core.version.deps.strategy;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 *         <p/>
 *         Version stratgey that returns the given version in the build dependency section if exists.
 */
public class ForcedVersionIfExistDependencyVersionStrategy implements DependencyVersionStrategy {
    @Override
    public Version getVersion(BuildDependency fetchedBuildDependency, ModuleStatus requestedStatus) {
        //The status is ignored
        return fetchedBuildDependency.getVersion();
    }
}
