package plugins.maven.pomparent;

import plugins.maven.pomparent.version.POMParentVersion;

/**
 * @author Gregory Boissinot
 */
public class BOMBuild extends AbstractPOMParent {

    public static final String BOM_BUILD_GROUP_ID = "bom";
    public static final String BOM_BUILD_ARTIFACT_ID = "bom-build";

    public BOMBuild(POMParentVersion version) {
        super(BOM_BUILD_GROUP_ID, BOM_BUILD_ARTIFACT_ID, version);
    }

    @Override
    public String writeXML() {
        //TODO
        return null;
    }
}
