package plugins.maven.pomparent;

import metadatarepo.core.version.Version;
import plugins.maven.pom.POMDependency;
import plugins.maven.pomparent.version.ParentVersion;

import java.util.List;

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
    public boolean hasParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public POMParent getParent() {
        throw new UnsupportedOperationException();
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
    public Version getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getClassifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<POMDependency> getDependencies() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String writeXML() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ParentVersion getParentVersion() {
        throw new UnsupportedOperationException();
    }
}
