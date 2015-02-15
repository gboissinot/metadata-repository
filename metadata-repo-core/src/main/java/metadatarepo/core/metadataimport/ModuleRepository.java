package metadatarepo.core.metadataimport;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;

import java.util.Collection;

/**
 * @author Gregory Boissinot
 */
public interface ModuleRepository {

    boolean isExistsModule(ModuleId moduleId);

    void insertModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies);

    void updateModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies);
}
