package metadatarepo.core.promotion;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleStatus;

/**
 * @author Gregory Boissinot
 */
public interface PromotionRepository {
    void promote(ModuleId requestedModuleId, ModuleStatus status);
}
