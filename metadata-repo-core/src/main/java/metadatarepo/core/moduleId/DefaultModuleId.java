package metadatarepo.core.moduleId;

/**
 * @author Gregory Boissinot
 */
class DefaultModuleId implements ModuleId {

    private final String org;

    private final String name;

    private final ModuleMetaVersion metaVersion;

    public DefaultModuleId(String org, String name, ModuleMetaVersion metaVersion) {
        this.org = org;
        this.name = name;
        this.metaVersion = metaVersion;
    }

    @Override
    public String getOrg() {
        return org;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ModuleMetaVersion getMetaVersion() {
        return metaVersion;
    }
}
