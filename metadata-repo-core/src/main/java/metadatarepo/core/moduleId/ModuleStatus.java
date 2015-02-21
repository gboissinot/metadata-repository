package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public interface ModuleStatus {

    void promote(ModuleStatusNotifier moduleStatusNotifier);

    void release(ModuleStatusNotifier moduleStatusNotifier);

    String getValue();

    boolean isDefault();
}
