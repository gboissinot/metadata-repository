package plugins.maven;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleIdFactory;
import metadatarepo.core.moduleId.NoModuleStatus;

/**
 * @author Gregory Boissinot
 */
public class MavenArtifactRequest {

    private String groupId;
    private String artifactId;
    private String version;
    private String classifier;
    private String type;

    public MavenArtifactRequest(String groupId, String artifactId, String version) {
        set(groupId, artifactId, version);
    }

    public MavenArtifactRequest(String groupId, String artifactId, String version, String classifier, String type) {
        set(groupId, artifactId, version);
        this.classifier = classifier;
        this.type = type;
    }

    private void set(String groupId, String artifactId, String version) {
        checkParameters(groupId, artifactId, version);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    private void checkParameters(String groupId, String artifactId, String version) {
        if (groupId == null) {
            throw new IllegalArgumentException();
        }
        if (artifactId == null) {
            throw new IllegalArgumentException();
        }
        if (version == null) {
            throw new IllegalArgumentException();
        }
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

    public String getClassifier() {
        return classifier;
    }

    public String getType() {
        return type;
    }

    public ModuleId getModuleId() {
        MavenVersion mavenVersion = new MavenVersion(version);
        if (mavenVersion.status == null) {
            return ModuleIdFactory.get(this.groupId, this.artifactId,
                    mavenVersion.version, new NoModuleStatus());
        }
        return ModuleIdFactory.get(this.groupId, this.artifactId,
                mavenVersion.version, mavenVersion.status);
    }

    public class MavenVersion {

        MavenVersion(String mavenVersion) {
            //TODO extract version and status
        }

        String version;
        String status;
    }


}
