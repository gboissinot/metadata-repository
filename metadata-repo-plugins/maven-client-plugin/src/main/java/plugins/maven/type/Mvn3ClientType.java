package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.version.ParentVersion;
import plugins.maven.pomparent.version.ParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn3ClientType extends AbstractMavenClientType {

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
    public ParentVersion getLatestBOMVersion() {
        return ParentVersionFactory.parentVersionWithWideRange();
    }
}
