package plugins.maven.pomparent;

import plugins.maven.pomparent.version.POMParentVersion;

/**
 * @author Gregory Boissinot
 */
public class NoPOMParent implements POMParent {

    private static NoPOMParent instance = new NoPOMParent();

    private NoPOMParent() {
    }

    public static NoPOMParent getInstance() {
        return instance;
    }

    @Override
    public String getGroupId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getArtifactId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String writeXML() {
        throw new UnsupportedOperationException();
    }

    @Override
    public POMParentVersion getParentVersion() {
        throw new UnsupportedOperationException();
    }
}
