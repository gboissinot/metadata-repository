package plugins.maven.pomparent;

import plugins.maven.pomparent.version.ParentVersion;

/**
 * @author Gregory Boissinot
 */
public class SimplePOMParent extends AbstractPOMParent {
    protected SimplePOMParent(String groupId, String artifactId, ParentVersion parentVersion) {
        super(groupId, artifactId, parentVersion);
    }

    @Override
    public String toXML() {
        throw new UnsupportedOperationException();
    }
}
