package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pomparent.POMParent;

/**
 * @author Gregory Boissinot
 */
public interface MavenClientType {

    DependencyVersionStrategy getDependencyVersionStrategy();

    POMParent getLatestDependencyBOM();
}
