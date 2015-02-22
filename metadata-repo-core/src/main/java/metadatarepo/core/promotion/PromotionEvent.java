package metadatarepo.core.promotion;

import messaging.Event;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleStatus;

/**
 * @author Gregory Boissinot
 */
public class PromotionEvent implements Event {

    private final ModuleId previousModuleId;

    private final ModuleStatus newModuleStatus;

    public PromotionEvent(ModuleId previousModuleId, ModuleStatus newModuleStatus) {
        this.previousModuleId = previousModuleId;
        this.newModuleStatus = newModuleStatus;
    }

    public ModuleId getPreviousModuleId() {
        return previousModuleId;
    }

    public ModuleStatus getNewModuleStatus() {
        return newModuleStatus;
    }
}
