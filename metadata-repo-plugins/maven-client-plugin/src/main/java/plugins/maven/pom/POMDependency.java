package plugins.maven.pom;

import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 */
public class POMDependency {

    private final String groupId;
    private final String artifactId;
    private final Version version;
    private String classifier;
    private String type;

    public POMDependency(String groupId, String artifactId, Version version) {
        checkMandatoryParameters(groupId, artifactId, version);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public POMDependency(String groupId, String artifactId, Version version, String classifier, String type) {
        checkMandatoryParameters(groupId, artifactId, version);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.classifier = classifier;
        this.type = type;
    }

    private void checkMandatoryParameters(String groupId, String artifactId, Version version) {
        if (groupId == null) {
            throw new IllegalArgumentException("A groupId is required.");
        }
        if (artifactId == null) {
            throw new IllegalArgumentException("A artifactId is required.");
        }
        if (version == null) {
            throw new IllegalArgumentException("An object metadatarepo.core.version is required.");
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Version getVersion() {
        return version;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getType() {
        return type;
    }
}
