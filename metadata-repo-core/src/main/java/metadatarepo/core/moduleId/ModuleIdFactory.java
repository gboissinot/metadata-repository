package metadatarepo.core.moduleId;

import metadatarepo.core.version.VersionFactory;

/**
 * @author Gregory Boissinot
 */
public class ModuleIdFactory {

    public static ModuleId get(String org, String name, String version, ModuleStatus moduleStatus) {
        return new DefaultModuleId(
                org, name,
                new ModuleMetaVersion(VersionFactory.get(version), moduleStatus));
    }

    public static ModuleId get(String org, String name, String version, String status) {
        return new DefaultModuleId(
                org, name,
                new ModuleMetaVersion(
                        VersionFactory.get(version), ModuleStatusFactory.get(status))
        );
    }
}
