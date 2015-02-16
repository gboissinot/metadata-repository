package plugins.maven.type;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public interface MavenClientType {

    DependencyVersionStrategy getDependencyVersionStrategy();

    ParentVersion getLatestBOMVersion();

    POMArtifact generatePOM(ModuleId moduleId);
}
