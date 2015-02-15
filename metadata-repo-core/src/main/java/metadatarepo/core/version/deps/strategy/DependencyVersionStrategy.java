package metadatarepo.core.version.deps.strategy;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 */
public interface DependencyVersionStrategy {
    Version getVersion(BuildDependency fetchedBuildDependency,
                       ModuleStatus requestedStatus);
}
