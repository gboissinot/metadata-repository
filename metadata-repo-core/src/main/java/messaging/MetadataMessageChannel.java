package messaging;

import metadatarepo.core.promotion.PromotionRepositoryReceiver;

/**
 * @author Gregory Boissinot
 */
public class MetadataMessageChannel {

    public MetadataMessageChannel() {
        register(PromotionRepositoryReceiver.class);
    }

    private <E extends Event> void register(Class<? extends Reveiver<E>> reveiverClass) {
        //TODO with Spring Integration
    }

    public void publishEvent(Event event) {
        //TODO with Spring Integration
    }
}
