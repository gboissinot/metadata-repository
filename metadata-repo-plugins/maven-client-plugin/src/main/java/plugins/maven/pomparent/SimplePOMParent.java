package plugins.maven.pomparent;

import plugins.maven.pomparent.version.POMParentVersion;

/**
 * @author Gregory Boissinot
 */
class SimplePOMParent extends AbstractPOMParent {
    public SimplePOMParent(String groupId, String artifactId, POMParentVersion parentVersion) {
        super(groupId, artifactId, parentVersion);
    }

    @Override
    public String writeXML() {
        throw new UnsupportedOperationException();
    }
}
