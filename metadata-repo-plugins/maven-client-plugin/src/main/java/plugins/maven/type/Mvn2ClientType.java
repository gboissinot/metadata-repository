package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.pomparent.version.ParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn2ClientType implements MavenClientType {

    private static POMParent POM_PARENT_WITH_LATEST =
            POMParentFactory.bomDeps(ParentVersionFactory.parentVersionWithLatestKeyword());

    private static Mvn2ClientType instance = new Mvn2ClientType();

    private Mvn2ClientType() {
    }

    public static Mvn2ClientType getInstance() {
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
