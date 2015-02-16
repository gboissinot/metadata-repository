package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the reading elements that we want to take into account.
 *
 * @author Gregory Boissinot
 */
public class POMArtifactStruct {

    private static final String SCHEMA_LOCATION = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd";
    private static final String MODEL_VERSION = "4.0.0";
    String schemaLocation = SCHEMA_LOCATION;
    String modelVersion = MODEL_VERSION;
    MavenPOMParentStuct parent;
    String groupId;
    String artifactId;
    String version;
    List<MavenPOMDependencyStruct> dependencies;
    List<MavenPOMProfile> profiles;

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
        String groupId = extractGroupId(pomContext);
        String artifactId = extractArtifactId();
        Version pomVersion = extractMavenPOMVersion(pomContext);
        List<POMDependency> dependencies = extractDependencies();
        return new POMArtifact(groupId, artifactId, pomVersion, dependencies);
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

    public static class MavenPOMProfile {
        String id;
        boolean activeByDefault;
        List<MavenPOMDependencyStruct> dependencies;
    }
}
