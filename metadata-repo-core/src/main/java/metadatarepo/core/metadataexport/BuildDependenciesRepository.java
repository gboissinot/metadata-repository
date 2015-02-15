package metadatarepo.core.metadataexport;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public interface BuildDependenciesRepository {

    List<BuildDependency> getDependencies(ModuleId moduleId);
}
