package plugins.maven.type;

import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public interface MavenClientType {
    DependencyVersionStrategy getDependencyVersionStrategy();

    ParentVersion getLatestBOMVersion();

    ParentVersion fixParentVersion(String version);
}
