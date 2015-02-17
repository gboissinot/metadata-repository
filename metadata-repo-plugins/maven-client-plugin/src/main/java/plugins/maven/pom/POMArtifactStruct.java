package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.List;

/**
 * Represents the reading elements that we want to take into account.
 *
 * @author Gregory Boissinot
 */
public class POMArtifactStruct {

    private static final String SCHEMA_LOCATION = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd";
    private static final String MODEL_VERSION = "4.0.0";
    private String schemaLocation = SCHEMA_LOCATION;
    private String modelVersion = MODEL_VERSION;
    public MavenPOMParentStuct parent;
    public String groupId;
    public String artifactId;
    public String version;
    public List<MavenPOMDependencyStruct> dependencies;
    public List<MavenPOMProfile> profiles;

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
