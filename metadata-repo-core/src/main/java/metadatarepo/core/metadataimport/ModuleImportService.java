package metadatarepo.core.metadataimport;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;

import java.util.Collection;

/**
 * @author Gregory Boissinot
 */
public class ModuleImportService {

    private ModuleRepository moduleRepository;

    public ModuleImportService(ModuleRepository moduleRepository) {
        if (moduleRepository == null) {
            throw new IllegalStateException("A module repository is required.");
        }
        this.moduleRepository = moduleRepository;
    }

    public void createModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies) {
        if (isExistsModule(moduleId)) {
            updateModule(moduleId, buildDependencies);
            return;
        }

        insertModule(moduleId, buildDependencies);
    }

    private boolean isExistsModule(ModuleId moduleId) {
        return moduleRepository.isExistsModule(moduleId);
    }

    private void insertModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies) {
        moduleRepository.insertModule(moduleId, buildDependencies);
    }

    private void updateModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies) {
        moduleRepository.updateModule(moduleId, buildDependencies);
    }
}
