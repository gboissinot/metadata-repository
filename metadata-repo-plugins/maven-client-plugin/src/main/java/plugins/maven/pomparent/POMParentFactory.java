package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public class POMParentFactory {

    public static POMParent bomBuild(ParentVersion parentVersion) {
        return new BOMBuild(parentVersion);
    }

    public static POMParent bomLatestDeps(ParentVersion parentVersion) {
        return new BOMLatestDeps(parentVersion);
    }

    public static POMParent noParent() {
        return NoPOMParent.getInstance();
    }

    public static POMParent regularParent(String parentGroupId, String parentArtifactId,
                                          ParentVersion parentVersion) {
        return new SimplePOMParent(parentGroupId, parentArtifactId, parentVersion);
    }
}
