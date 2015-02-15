package metadatarepo.core.metadataimport;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.moduleId.ModuleId;

import java.util.Collection;


/**
 * @author Gregory Boissinot
 */
public class MongoDBModuleRepository implements ModuleRepository {
    @Override
    public boolean isExistsModule(ModuleId moduleId) {
        //TODO
        return false;
    }

    @Override
    public void insertModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies) {
        //TODO
    }

    @Override
    public void updateModule(ModuleId moduleId, Collection<BuildDependency> buildDependencies) {
        //TODO
    }
}
