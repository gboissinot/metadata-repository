package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import metadatarepo.core.version.NoVersion;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import plugins.maven.pomparent.NoPOMParent;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;

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

    public POMArtifact(POMParent parent, String groupId, String artifactId, Version version) {
        set(parent, groupId, artifactId, version);
    }

    public POMArtifact(POMParent parent, String groupId, String artifactId, Version version, List<POMDependency> dependencies) {
        set(parent, groupId, artifactId, version);
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

    @Override
    public boolean hasParent() {
        return !(parent instanceof NoPOMParent);
    }

    @Override
    public POMParent getParent() {
        return parent;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public String getClassifier() {
        return null;
    }

    @Override
    public String getType() {
        return "pom";
    }

    @Override
    public List<POMDependency> getDependencies() {
        return Collections.unmodifiableList(dependencies);
    }

    private POMArtifactStruct buildPOMStruct() {
        POMArtifactStruct struct = new POMArtifactStruct();
        struct.groupId = getGroupId();
        struct.artifactId = getArtifactId();
        buildVersionStruct(struct);
        buildParentStruct(struct);
        buildDependenciesStruct(struct);
        return struct;
    }

    private void buildVersionStruct(POMArtifactStruct struct) {
        assert struct != null;
        Version version = getVersion();
        if (version instanceof NoVersion) {
            return;
        }
        struct.version = version.getValue();
    }

    private void buildParentStruct(POMArtifactStruct struct) {
        assert struct != null;
        if (this.hasParent()) {
            POMParent parent = getParent();
            struct.parent = new POMArtifactStruct.MavenPOMParentStuct();
            struct.parent.groupId = parent.getGroupId();
            struct.parent.artifactId = parent.getArtifactId();
            struct.parent.version = parent.getParentVersion().getValue();
        }
    }

    private void buildDependenciesStruct(POMArtifactStruct struct) {
        assert struct != null;
        List<POMDependency> dependencies = getDependencies();
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

    public static class POMArtifactStruct {

        private static final String SCHEMA_LOCATION = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd";
        private static final String MODEL_VERSION = "4.0.0";
        String schemaLocation = SCHEMA_LOCATION;
        String modelVersion = MODEL_VERSION;
        MavenPOMParentStuct parent;
        String groupId;
        String artifactId;
        String version;
        List<MavenPOMDependencyStruct> dependencies;

        /**
         * Serializes the structure in String
         *
         * @return the POM content in a XML format (used by any compatible Maven Client)
         */
        public String toXML() {
            XStream xstream = new XStream(new DomDriver("UTF-8"));
            xstream.alias("project", POMArtifactStruct.class);
            xstream.alias("dependency", POMArtifactStruct.MavenPOMDependencyStruct.class);
            xstream.aliasAttribute(POMArtifactStruct.class, "schemaLocation", "xsi:schemaLocation");
            return xstream.toXML(this);
        }

        /**
         * Build a Maven POM object graph.
         * All information from the built object is stored in the metadata store.
         *
         * @param pomContext The input maven context if the given maven pom use optional fields
         * @return the Maven POM object without a Maven POM Parent
         */
        public POMArtifact fromXML(POMContext pomContext) {
            Version pomVersion = extractMavenPOMVersion(pomContext);
            String groupId = extractGroupId(pomContext);
            String artifactId = extractArtifactId();
            List<POMDependency> dependencies = extractDependencies();
            return new POMArtifact(POMParentFactory.noParent(), groupId, artifactId, pomVersion, dependencies);
        }

        private List<POMDependency> extractDependencies() {
            if (this.dependencies == null) {
                return Collections.EMPTY_LIST;
            }
            List<POMDependency> dependenciesResult = new ArrayList<>();
            for (POMArtifactStruct.MavenPOMDependencyStruct structDependency : this.dependencies) {
                String structDepVersion = structDependency.version;
                Version versionDependency;
                if (structDepVersion == null) {
                    versionDependency = VersionFactory.noVersion();
                } else {
                    versionDependency = VersionFactory.get(structDepVersion);
                }
                POMDependency POMDependency = new POMDependency(
                        structDependency.groupId,
                        structDependency.artifactId,
                        versionDependency,
                        structDependency.classifier,
                        structDependency.type);
                dependenciesResult.add(POMDependency);
            }
            return dependenciesResult;
        }

        private String extractArtifactId() {
            validateNotNull("artifactId", this.artifactId);
            return this.artifactId;
        }

        private String extractGroupId(POMContext pomContext) {
            if (this.groupId == null) {
                return pomContext.getGroupId();
            }
            return this.groupId;
        }

        private Version extractMavenPOMVersion(POMContext pomContext) {
            if (this.version == null) {
                return VersionFactory.get(pomContext.getVersion());
            }
            return VersionFactory.get(this.version);
        }

        private void validateNotNull(String filedName, String fieldValue) {
            if (fieldValue == null) {
                throw new ConversionExcetion(String.format("%s must have a value.", filedName));
            }
        }

        public static class MavenPOMParentStuct {
            String groupId;
            String artifactId;
            String version;
        }

        public static class MavenPOMDependencyStruct {
            String groupId;
            String artifactId;
            String version;
            String classifier;
            String type;
        }
    }
}
