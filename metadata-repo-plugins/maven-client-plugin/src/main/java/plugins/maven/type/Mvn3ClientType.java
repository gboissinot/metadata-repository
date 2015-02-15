package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.pomparent.version.ParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn3ClientType extends AbstractMavenClientType {

    private static POMParent POM_PARENT_WITH_LATEST =
            POMParentFactory.bomDeps(ParentVersionFactory.parentVersionWithWideRange());

    private static Mvn3ClientType instance = new Mvn3ClientType();

    private Mvn3ClientType() {
    }

    public static Mvn3ClientType getInstance() {
        return instance;
    }

    @Override
    public DependencyVersionStrategy getDependencyVersionStrategy() {
        return new ForcedVersionIfExistDependencyVersionStrategy();
    }

    @Override
    public POMParent getLatestDependencyBOM() {
        return POM_PARENT_WITH_LATEST;
    }
}
