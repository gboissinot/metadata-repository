package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
abstract class AbstractPOMParent implements POMParent {

    private final String groupId;
    private final String artifactId;
    private final ParentVersion version;

    protected AbstractPOMParent(String groupId, String artifactId, ParentVersion parentVersion) {
        checkParameters(groupId, artifactId, parentVersion);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = parentVersion;
    }

    private void checkParameters(String groupId, String artifactId, ParentVersion parentVersion) {
        if (groupId == null) {
            throw new IllegalArgumentException("A groupId is required.");
        }
        if (artifactId == null) {
            throw new IllegalArgumentException("An artifactId is required.");
        }
        if (parentVersion == null) {
            throw new IllegalArgumentException("A maven version is required.");
        }
        String version = parentVersion.getValue();
        if (version == null) {
            throw new IllegalStateException("A Maven POM Parent must have a version.");
        }
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public ParentVersion getParentVersion() {
        return version;
    }
}
