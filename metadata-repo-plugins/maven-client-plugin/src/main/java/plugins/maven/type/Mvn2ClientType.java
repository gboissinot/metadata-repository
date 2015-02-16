package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.pomparent.version.ParentVersion;
import plugins.maven.pomparent.version.ParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn2ClientType extends AbstractMavenClientType {

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
    public ParentVersion getLatestBOMVersion() {
        return ParentVersionFactory.parentVersionWithLatestKeyword();
    }
}
