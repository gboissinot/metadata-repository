package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import metadatarepo.core.version.NoVersion;
import metadatarepo.core.version.Version;
import plugins.maven.pomparent.NoPOMParent;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.pomparent.version.ParentVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class POMArtifact implements POM {

    private POMParent parent;
    private String groupId;
    private String artifactId;
    private Version version;
    private List<POMDependency> dependencies = Collections.EMPTY_LIST;

    public POMArtifact(String groupId, String artifactId, Version version) {
        set(POMParentFactory.noParent(), groupId, artifactId, version);
    }

    public POMArtifact(String groupId, String artifactId, Version version, List<POMDependency> dependencies) {
        set(POMParentFactory.noParent(), groupId, artifactId, version);
        this.dependencies = (dependencies == null) ? Collections.EMPTY_LIST : dependencies;
    }

    public POMArtifact(String parentGroupId,
                       String parentArtifactId,
                       ParentVersion parentVersion,
                       String groupId, String artifactId, Version version) {
        set(POMParentFactory.regularParent(parentGroupId, parentArtifactId, parentVersion), groupId, artifactId, version);
    }

    public POMArtifact(String parentGroupId,
                       String parentArtifactId,
                       ParentVersion parentVersion,
                       String groupId, String artifactId, Version version, List<POMDependency> dependencies) {
        set(POMParentFactory.regularParent(parentGroupId, parentArtifactId, parentVersion), groupId, artifactId, version);
        this.dependencies = (dependencies == null) ? Collections.EMPTY_LIST : dependencies;
    }

    private void set(POMParent parent, String groupId, String artifactId, Version version) {
        checkParameters(parent, groupId, artifactId, version);
        this.parent = parent;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    private void checkParameters(POMParent parent, String groupId, String artifactId, Version version) {
        if (parent == null) {
            throw new IllegalArgumentException("A parent is required.");
        }
        if (groupId == null) {
            throw new IllegalArgumentException("An groupId is required.");
        }
        if (artifactId == null) {
            throw new IllegalArgumentException("An artifactId is required.");
        }
        if (version == null) {
            throw new IllegalArgumentException("A metadatarepo.core.version is required.");
        }
    }

    public POMParent getParent() {
        return parent;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Version getVersion() {
        return version;
    }

    public List<POMDependency> getDependencies() {
        return Collections.unmodifiableList(dependencies);
    }

    @Override
    public String writeXML() {
        POMArtifactStruct pomArtifactStruct = buildPOMStruct();
        return pomArtifactStruct.toXML();
    }

    private POMArtifactStruct deserialize(String mavenPOMContent) {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.alias("project", POMArtifactStruct.class);
        xstream.alias("dependency", POMArtifactStruct.MavenPOMDependencyStruct.class);
        try {
            return (POMArtifactStruct) xstream.fromXML(mavenPOMContent);
        } catch (Throwable e) {
            throw new ConversionExcetion(e);
        }
    }

    private boolean hasParent() {
        return !(parent instanceof NoPOMParent);
    }

    private POMArtifactStruct buildPOMStruct() {
        POMArtifactStruct struct = new POMArtifactStruct();
        struct.groupId = this.groupId;
        struct.artifactId = this.artifactId;
        buildVersionStruct(struct);
        buildParentStruct(struct);
        buildDependenciesStruct(struct);
        return struct;
    }

    private void buildVersionStruct(POMArtifactStruct struct) {
        assert struct != null;
        Version version = this.version;
        if (version instanceof NoVersion) {
            return;
        }
        struct.version = version.getValue();
    }

    private void buildParentStruct(POMArtifactStruct struct) {
        assert struct != null;
        if (this.hasParent()) {
            POMParent parent = this.parent;
            struct.parent = new POMArtifactStruct.MavenPOMParentStuct();
            struct.parent.groupId = parent.getGroupId();
            struct.parent.artifactId = parent.getArtifactId();
            struct.parent.version = parent.getParentVersion().getValue();
        }
    }

    private void buildDependenciesStruct(POMArtifactStruct struct) {
        assert struct != null;
        List<POMDependency> dependencies = this.dependencies;
        if (dependencies == null || dependencies.isEmpty()) {
            return;
        }

        List<POMArtifactStruct.MavenPOMDependencyStruct> structDependencies = new ArrayList<POMArtifactStruct.MavenPOMDependencyStruct>();
        for (POMDependency dependency : dependencies) {
            POMArtifactStruct.MavenPOMDependencyStruct dependencyStruct = new POMArtifactStruct.MavenPOMDependencyStruct();
            dependencyStruct.groupId = dependency.getGroupId();
            dependencyStruct.artifactId = dependency.getArtifactId();
            dependencyStruct.version = dependency.getVersion().getValue();
            dependencyStruct.classifier = dependency.getClassifier();
            dependencyStruct.type = dependency.getType();
            structDependencies.add(dependencyStruct);
        }
        struct.dependencies = structDependencies;
    }
}
