package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
public interface ModuleStatusNotifier {

    void changeStatus(ModuleStatus moduleStatus);
}
