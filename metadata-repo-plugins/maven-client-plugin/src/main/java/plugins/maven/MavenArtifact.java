package plugins.maven;

import metadatarepo.core.version.Version;

/**
 * @author Gregory Boissinot
 */
public interface MavenArtifact {
    String getGroupId();

    String getArtifactId();

    Version getVersion();

    String getClassifier();

    String getType();
}
