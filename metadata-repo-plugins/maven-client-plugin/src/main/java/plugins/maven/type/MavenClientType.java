package plugins.maven.type;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pomparent.POMParent;

/**
 * @author Gregory Boissinot
 */
public interface MavenClientType {

    DependencyVersionStrategy getDependencyVersionStrategy();

    POMParent getLatestDependencyBOM();

    POMArtifact generatePOM(ModuleId moduleId);
}
