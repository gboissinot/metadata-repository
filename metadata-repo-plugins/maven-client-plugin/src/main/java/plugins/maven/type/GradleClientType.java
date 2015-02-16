package plugins.maven.type;

import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.latest.LatestOrForcedDependencyVersionStrategy;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
class GradleClientType extends AbstractMavenClientType {

    private static GradleClientType instance = new GradleClientType();

    private GradleClientType() {
    }

    public static GradleClientType getInstance() {
        return instance;
    }

    @Override
    public DependencyVersionStrategy getDependencyVersionStrategy() {
        return new LatestOrForcedDependencyVersionStrategy();
    }

    @Override
    public ParentVersion getLatestBOMVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public POMArtifact generatePOM(ModuleId moduleId) {
        return new POMArtifact(
                moduleId.getOrg(), moduleId.getName(), getPOMVersion(moduleId),
                getPOMMavenDependencies(getDependencyVersionStrategy(), moduleId)
        );
    }
}
