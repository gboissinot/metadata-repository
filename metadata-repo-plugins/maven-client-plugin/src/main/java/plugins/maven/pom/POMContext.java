package plugins.maven.pom;

/**
 * @author Gregory Boissinot
 */
public class POMContext {

    private String groupId;
    private String artifactId;
    private String version;

    public POMContext(String groupId, String artifactId, String version) {
        if (groupId == null) {
            throw new IllegalArgumentException("A groupId is required.");
        }
        if (artifactId == null) {
            throw new IllegalArgumentException("An artifactId is required.");
        }
        if (version == null) {
            throw new IllegalArgumentException("A version is required.");
        }
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }
}
