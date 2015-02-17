package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.version.POMParentVersion;
import plugins.maven.pomparent.version.POMParentVersionFactory;

/**
 * @author Gregory Boissinot
 */
class Mvn2ClientType implements MavenClientType {

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
    public POMParentVersion getLatestBOMVersion() {
        return POMParentVersionFactory.parentVersionWithLatestKeyword();
    }

    @Override
    public POMParentVersion fixParentVersion(String version) {
        if (isVersionRange(version)) {
            return getLatestBOMVersion();
        }
        return POMParentVersionFactory.parentVersionWithGivenVersionValue(version);
    }

    private boolean isVersionRange(String version) {
        return version.contains("[");
    }
}
