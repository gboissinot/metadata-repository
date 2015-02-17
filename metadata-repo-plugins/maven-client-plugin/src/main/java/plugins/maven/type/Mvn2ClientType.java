package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import metadatarepo.core.version.deps.strategy.ForcedVersionIfExistDependencyVersionStrategy;
import plugins.maven.pomparent.version.ParentVersion;
import plugins.maven.pomparent.version.ParentVersionFactory;

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
    public ParentVersion getLatestBOMVersion() {
        return ParentVersionFactory.parentVersionWithLatestKeyword();
    }

    @Override
    public ParentVersion fixParentVersion(String version) {
        //is a range
        if (isVersionRange(version)) {
            return getLatestBOMVersion();
        }
        return ParentVersionFactory.parentVersionWithGivenVersionValue(version);
    }

    private boolean isVersionRange(String version) {
        return version.contains("[");
    }
}
