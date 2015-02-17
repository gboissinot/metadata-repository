package plugins.maven.pomparent;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pomparent.version.POMParentVersion;
import plugins.maven.type.MavenClientType;

/**
 * @author Gregory Boissinot
 */
public class StubMavenClientType implements MavenClientType {
    @Override
    public DependencyVersionStrategy getDependencyVersionStrategy() {
        return null;
    }

    @Override
    public POMParentVersion getLatestBOMVersion() {
        return null;
    }

    @Override
    public POMParentVersion fixParentVersion(String version) {
        return null;
    }
}
