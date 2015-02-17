package plugins.maven.pomparent;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pomparent.version.ParentVersion;
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
    public ParentVersion getLatestBOMVersion() {
        return null;
    }

    @Override
    public ParentVersion fixParentVersion(String version) {
        return null;
    }
}
