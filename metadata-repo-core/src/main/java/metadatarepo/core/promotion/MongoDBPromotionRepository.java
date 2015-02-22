package metadatarepo.core.promotion;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleStatus;

/**
 * @author Gregory Boissinot
 */
public class MongoDBPromotionRepository implements PromotionRepository {
    @Override
    public void promote(ModuleId requestedModuleId, ModuleStatus status) {
        //TODO
    }
}
