package plugins.maven.pomparent;

import org.junit.BeforeClass;
import org.junit.Test;
import plugins.maven.pom.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gregory Boissinot
 */
public class POMDeserializerTest {

    private static final String POM_GROUPID = "testGroupId";
    private static final String POM_ARTIFACTID = "tesArtifactId";
    private static final String POM_VERSION = "1.0";

    private static POMContext pomContext;
    private static POMBuilder pomBuilder;

    @BeforeClass
    public static void setup() {
        pomBuilder = new POMBuilder();
        pomContext = new POMContext(POM_GROUPID, POM_ARTIFACTID, POM_VERSION);
    }

    @Test
    public void noGroupId() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "</project>";
        POMArtifact pomArtifact = pomBuilder.buildFromXML(actualContent, pomContext);
        assertEquals(POM_GROUPID, pomArtifact.getGroupId());
        assertEquals(POM_ARTIFACTID, pomArtifact.getArtifactId());
        assertEquals(POM_VERSION, pomArtifact.getVersion().getValue());
    }

    @Test(expected = ConversionExcetion.class)
    public void noArtifactId() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "</project>";
        pomBuilder.buildFromXML(actualContent, pomContext);
    }

    @Test
    public void noVersion() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "</project>";
        POMArtifact pomArtifact = pomBuilder.buildFromXML(actualContent, pomContext);
        assertEquals(POM_GROUPID, pomArtifact.getGroupId());
        assertEquals(POM_ARTIFACTID, pomArtifact.getArtifactId());
        assertEquals(POM_VERSION, pomArtifact.getVersion().getValue());
    }

    @Test
    public void unknownElement() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "  <unknown>" + POM_ARTIFACTID + "</unknown>\n" +
                "</project>";
        pomBuilder.buildFromXML(actualContent, pomContext);
        assertTrue(true);
    }

    @Test
    public void regularSimpleDeserialization() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "  <dependencies>\n" +
                "    <dependency>\n" +
                "      <groupId>junit</groupId>\n" +
                "      <artifactId>junit</artifactId>\n" +
                "      <version>4.8.1</version>\n" +
                "    </dependency>\n" +
                "  </dependencies>\n" +
                "</project>";
        POMArtifact pomArtifact = pomBuilder.buildFromXML(actualContent, pomContext);
        assertEquals(POM_GROUPID, pomArtifact.getGroupId());
        assertEquals(POM_ARTIFACTID, pomArtifact.getArtifactId());
        assertEquals(POM_VERSION, pomArtifact.getVersion().getValue());
        List<POMDependency> dependencies = pomArtifact.getDependencies();
        assertNotNull(dependencies);
        assertEquals(1, dependencies.size());
        for (POMDependency dependency : dependencies) {
            assertEquals("junit", dependency.getGroupId());
            assertEquals("junit", dependency.getArtifactId());
            assertEquals("4.8.1", dependency.getVersion().getValue());
        }
    }

    @Test
    public void regularCustomDeserialization() {
        String actualContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "  <dependencies>\n" +
                "    <dependency>\n" +
                "      <groupId>junit</groupId>\n" +
                "      <artifactId>junit</artifactId>\n" +
                "      <version>4.8.1</version>\n" +
                "      <classifier>sources</classifier>\n" +
                "      <type>jar</type>\n" +
                "    </dependency>\n" +
                "    <dependency>\n" +
                "      <groupId>log4j</groupId>\n" +
                "      <artifactId>log4j</artifactId>\n" +
                "      <version>1.2.14</version>\n" +
                "    </dependency>\n" +
                "  </dependencies>\n" +
                "</project>";
        POMArtifact pomArtifact = pomBuilder.buildFromXML(actualContent, pomContext);
        assertEquals(POM_GROUPID, pomArtifact.getGroupId());
        assertEquals(POM_ARTIFACTID, pomArtifact.getArtifactId());
        assertEquals(POM_VERSION, pomArtifact.getVersion().getValue());
        List<POMDependency> dependencies = pomArtifact.getDependencies();
        assertNotNull(dependencies);
        assertEquals(2, dependencies.size());

        POMDependency pomDependency1 = dependencies.get(0);
        assertEquals("junit", pomDependency1.getGroupId());
        assertEquals("junit", pomDependency1.getArtifactId());
        assertEquals("4.8.1", pomDependency1.getVersion().getValue());
        assertEquals("sources", pomDependency1.getClassifier());
        assertEquals("jar", pomDependency1.getType());

        POMDependency pomDependency2 = dependencies.get(1);
        assertEquals("log4j", pomDependency2.getGroupId());
        assertEquals("log4j", pomDependency2.getArtifactId());
        assertEquals("1.2.14", pomDependency2.getVersion().getValue());
        assertNull(pomDependency2.getClassifier());
        assertNull(pomDependency2.getType());
    }

    @Test
    public void deserializationAndSerialization() {
        String content = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "  <dependencies>\n" +
                "    <dependency>\n" +
                "      <groupId>junit</groupId>\n" +
                "      <artifactId>junit</artifactId>\n" +
                "      <version>4.8.1</version>\n" +
                "      <classifier>sources</classifier>\n" +
                "      <type>jar</type>\n" +
                "    </dependency>\n" +
                "    <dependency>\n" +
                "      <groupId>log4j</groupId>\n" +
                "      <artifactId>log4j</artifactId>\n" +
                "      <version>1.2.14</version>\n" +
                "    </dependency>\n" +
                "  </dependencies>\n" +
                "</project>";
        POMArtifact pomArtifact = pomBuilder.buildFromXML(content, pomContext);
        String resultContent = pomArtifact.writeXML();
        assertEquals(content, resultContent);
    }
}
