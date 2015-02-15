package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.latest.LatestOrForcedDependencyVersionStrategy;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;

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
    public POMParent getLatestDependencyBOM() {
        return POMParentFactory.noParent();
    }
}
