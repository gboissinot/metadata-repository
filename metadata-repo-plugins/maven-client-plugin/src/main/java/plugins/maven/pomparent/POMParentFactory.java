package plugins.maven.pomparent;

import plugins.maven.pomparent.version.POMParentVersion;

/**
 * @author Gregory Boissinot
 */
public class POMParentFactory {

    public static POMParent bomBuild(POMParentVersion parentVersion) {
        return new BOMBuild(parentVersion);
    }

    public static POMParent bomLatestDeps(POMParentVersion parentVersion) {
        return new BOMLatestDeps(parentVersion);
    }

    public static POMParent noParent() {
        return NoPOMParent.getInstance();
    }

    public static POMParent regularParent(String parentGroupId, String parentArtifactId,
                                          POMParentVersion parentVersion) {
        return new SimplePOMParent(parentGroupId, parentArtifactId, parentVersion);
    }
}
