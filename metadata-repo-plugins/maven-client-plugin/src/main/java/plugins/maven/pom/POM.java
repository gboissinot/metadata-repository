package plugins.maven.pom;

import plugins.maven.MavenArtifact;
import plugins.maven.pomparent.POMParent;

import java.util.List;

/**
 * @author Gregory Boissinot
 */
public interface POM extends MavenArtifact {

    boolean hasParent();

    POMParent getParent();

    List<POMDependency> getDependencies();

    String writeXML();
}
