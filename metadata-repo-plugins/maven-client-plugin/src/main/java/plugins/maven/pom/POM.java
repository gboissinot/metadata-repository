package plugins.maven.pom;

import metadatarepo.core.version.Version;
import plugins.maven.pomparent.POMParent;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public interface POM {

    boolean hasParent();

    POMParent getParent();

    String getGroupId();

    String getArtifactId();

    Version getVersion();

    List<POMDependency> getDependencies();

    String toXML();
}
