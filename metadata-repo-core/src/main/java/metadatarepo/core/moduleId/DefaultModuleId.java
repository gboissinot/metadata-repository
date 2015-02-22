package metadatarepo.core.moduleId;

import metadatarepo.core.metadataimport.ModuleImportService;
import metadatarepo.core.promotion.PromotionService;

/**
 * @author Gregory Boissinot
 */
class DefaultModuleId implements ModuleId {

    private ModuleImportService moduleImportService;
    private PromotionService promotionService;

    private final String org;

    private final String name;

    private ModuleMetaVersion metaVersion;

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

    @Override
    public void promote() {
        ModuleId requestedModuleId = ModuleIdFactory.get(getOrg(), getName(), metaVersion.getVersion().getValue(), metaVersion.getStatus());
        metaVersion.promote();
        promotionService.firePromotion(requestedModuleId, metaVersion.getStatus());
    }

    @Override
    public void release() {
        ModuleId requestedModuleId = ModuleIdFactory.get(getOrg(), getName(), metaVersion.getVersion().getValue(), metaVersion.getStatus());
        metaVersion.release();
        promotionService.firePromotion(requestedModuleId, metaVersion.getStatus());
    }

    @Override
    public void deprecate() {

    }
}
