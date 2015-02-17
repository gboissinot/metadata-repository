package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.version.POMParentVersion;
import plugins.maven.pomparent.version.POMParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn3ClientType implements MavenClientType {

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
    public POMParentVersion getLatestBOMVersion() {
        return POMParentVersionFactory.parentVersionWithWideRange();
    }

    @Override
    public POMParentVersion fixParentVersion(String version) {
        if (isLatestKeyword(version)) {
            return getLatestBOMVersion();
        }
        return POMParentVersionFactory.parentVersionWithGivenVersionValue(version);
    }

    private boolean isLatestKeyword(String version) {
        return "RELEASE".equals(version) || "LATEST".equals(version);
    }
}
