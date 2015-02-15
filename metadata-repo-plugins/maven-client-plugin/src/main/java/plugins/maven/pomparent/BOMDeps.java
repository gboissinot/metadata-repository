package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public class BOMDeps extends AbstractPOMParent {

    public static final String BOM_DEPS_GROUP_ID = "bom";
    public static final String BOM_DEPS_ARTIFACT_ID = "bom-deps";

    public BOMDeps(ParentVersion version) {
        super(BOM_DEPS_GROUP_ID, BOM_DEPS_ARTIFACT_ID, version);
    }


    @Override
    public String toXML() {
        //TODO
        return null;
    }
}
