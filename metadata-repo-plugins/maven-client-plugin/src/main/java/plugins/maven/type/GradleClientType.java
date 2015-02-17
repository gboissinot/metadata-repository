package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.latest.LatestOrForcedDependencyVersionStrategy;
import plugins.maven.pomparent.version.POMParentVersion;

/**
 * @author Gregory Boissinot
 */
class GradleClientType implements MavenClientType {

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
    public POMParentVersion getLatestBOMVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public POMParentVersion fixParentVersion(String version) {
        throw new UnsupportedOperationException();
    }
}
