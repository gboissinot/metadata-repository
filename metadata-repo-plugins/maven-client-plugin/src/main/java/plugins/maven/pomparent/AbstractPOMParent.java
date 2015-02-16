package plugins.maven.pomparent;

import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import plugins.maven.pom.POMDependency;
import plugins.maven.pomparent.version.ParentVersion;

import java.util.Collections;
import java.util.List;

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

    @Override
    public boolean hasParent() {
        //We support only one hierarchy
        return false;
    }

    @Override
    public POMParent getParent() {
        //We support only one hierarchy
        return POMParentFactory.noParent();
    }

    @Override
    public Version getVersion() {
        return VersionFactory.get(version.getValue());
    }

    @Override
    public List<POMDependency> getDependencies() {
        //We don't support dependencies in Parent
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getClassifier() {
        return null;
    }

    @Override
    public String getType() {
        return "pom";
    }
}
