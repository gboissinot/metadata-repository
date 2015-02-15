package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public class POMParentFactory {

    public static POMParent bomDeps(ParentVersion parentVersion) {
        return new BOMDeps(parentVersion);
    }

    public static POMParent noParent() {
        return NoPOMParent.getInstance();
    }

    public static POMParent bomBuild(ParentVersion parentVersion) {
        return new BOMBuild(parentVersion);
    }
}
