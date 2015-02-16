package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public class BOMLatestDeps extends AbstractPOMParent {

    public static final String BOM_DEPS_GROUP_ID = "bom";
    public static final String BOM_DEPS_ARTIFACT_ID = "bom-deps";

    public BOMLatestDeps(ParentVersion version) {
        super(BOM_DEPS_GROUP_ID, BOM_DEPS_ARTIFACT_ID, version);
    }


    @Override
    public String writeXML() {
        //TODO
        return null;
    }
}
