package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
class SimplePOMParent extends AbstractPOMParent {
    public SimplePOMParent(String groupId, String artifactId, ParentVersion parentVersion) {
        super(groupId, artifactId, parentVersion);
    }

    @Override
    public String writeXML() {
        throw new UnsupportedOperationException();
    }
}
