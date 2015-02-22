package metadatarepo.core.promotion;

import messaging.MetadataMessageChannel;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleStatus;

/**
 * @author Gregory Boissinot
 */
public class PromotionService {

    private final MetadataMessageChannel messageChannel;

    public PromotionService(MetadataMessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    public void firePromotion(ModuleId requestedModuleId, ModuleStatus newStatus) {
        messageChannel.publishEvent(new PromotionEvent(requestedModuleId, newStatus));
    }
}
