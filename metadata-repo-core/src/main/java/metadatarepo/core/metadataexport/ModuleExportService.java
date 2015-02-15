package metadatarepo.core.metadataexport;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class ModuleExportService {

    private final BuildDependenciesRepository buildDependenciesRepository;

    public ModuleExportService(BuildDependenciesRepository buildDependenciesRepository) {
        this.buildDependenciesRepository = buildDependenciesRepository;
    }

    public List<BuildDependency> getBuildDependencies(DependencyVersionStrategy dependencyVersionStrategy,
                                                      ModuleId moduleId) {
        List<BuildDependency> fetchedDependencies = buildDependenciesRepository.getDependencies(moduleId);
        return Collections.unmodifiableList(applyDependenciesStrategy(dependencyVersionStrategy,
                fetchedDependencies, moduleId.getMetaVersion().getStatus()));
    }

    private List<BuildDependency> applyDependenciesStrategy(DependencyVersionStrategy dependencyVersionStrategy,
                                                            List<BuildDependency> fetchedDependencies,
                                                            ModuleStatus status) {
        List<BuildDependency> resultDependenciesSet = new ArrayList<>();
        for (BuildDependency buildDependency : fetchedDependencies) {
            Version version = dependencyVersionStrategy.getVersion(buildDependency, status);
            BuildDependency newBuildDependency = new BuildDependency(buildDependency.getOrg(), buildDependency.getName(), version, buildDependency.getExtraAttributes());
            resultDependenciesSet.add(newBuildDependency);
        }
        return resultDependenciesSet;
    }
}
