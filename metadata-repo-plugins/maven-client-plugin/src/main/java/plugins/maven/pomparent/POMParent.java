package plugins.maven.pomparent;

import plugins.maven.pom.POM;
import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public interface POMParent extends POM {

    String getGroupId();

    String getArtifactId();

    ParentVersion getParentVersion();
}
