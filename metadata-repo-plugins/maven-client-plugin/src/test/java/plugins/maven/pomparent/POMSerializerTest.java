package plugins.maven.pomparent;

import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pom.POMBuilder;
import plugins.maven.pom.POMContext;
import plugins.maven.pom.POMDependency;
import plugins.maven.pomparent.version.ParentVersionFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Gregory Boissinot
 */
public class POMSerializerTest {

    private static final String POM_GROUPID = "testGroupId";
    private static final String POM_ARTIFACTID = "tesArtifactId";
    private static final String POM_VERSION = "1.0";
    private static final String POM_PARENT_VERSION = "2.0";

    private static Version pomVersion;

    @BeforeClass
    public static void setup() {
        pomVersion = VersionFactory.get(POM_VERSION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullParent() {
        final POMArtifact pomArtifact = new POMArtifact(null, POM_GROUPID, POM_ARTIFACTID, pomVersion);
        pomArtifact.toXML();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullGroupId() {
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), null, POM_ARTIFACTID, pomVersion);
        pomArtifact.toXML();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArtifactId() {
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, null, pomVersion);
        pomArtifact.toXML();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullVersion() {
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, null);
        pomArtifact.toXML();
    }

    @Test
    public void minimalPOM() {
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, pomVersion);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "</project>";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void minimalPOMWithBOMDepsParent() {
        final POMArtifact pomArtifact = new POMArtifact(
                new BOMDeps(ParentVersionFactory.parentVersionWithGivenVersionValue(POM_PARENT_VERSION)),
                POM_GROUPID,
                POM_ARTIFACTID,
                pomVersion);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <parent>\n" +
                "    <groupId>" + BOMDeps.BOM_DEPS_GROUP_ID + "</groupId>\n" +
                "    <artifactId>" + BOMDeps.BOM_DEPS_ARTIFACT_ID + "</artifactId>\n" +
                "    <version>" + POM_PARENT_VERSION + "</version>\n" +
                "  </parent>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "</project>";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void minimalPOMWithBOMbuildParent() {
        final POMArtifact pomArtifact = new POMArtifact(
                new BOMBuild(ParentVersionFactory.parentVersionWithGivenVersionValue(POM_PARENT_VERSION)),
                POM_GROUPID, POM_ARTIFACTID, pomVersion);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <parent>\n" +
                "    <groupId>" + BOMBuild.BOM_BUILD_GROUP_ID + "</groupId>\n" +
                "    <artifactId>" + BOMBuild.BOM_BUILD_ARTIFACT_ID + "</artifactId>\n" +
                "    <version>" + POM_PARENT_VERSION + "</version>\n" +
                "  </parent>\n" +
                "  <groupId>" + POM_GROUPID + "</groupId>\n" +
                "  <artifactId>" + POM_ARTIFACTID + "</artifactId>\n" +
                "  <version>" + POM_VERSION + "</version>\n" +
                "</project>";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void pomWithOneDep() {
        List<POMDependency> dependencies = new ArrayList<>();
        dependencies.add(new POMDependency("junit", "junit", VersionFactory.get("4.8.1")));
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, pomVersion, dependencies);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
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
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void pomWithTwoDeps() {
        List<POMDependency> dependencies = new ArrayList<>();
        dependencies.add(new POMDependency("junit", "junit", VersionFactory.get("4.8.1")));
        dependencies.add(new POMDependency("log4j", "log4j", VersionFactory.get("1.2.14")));
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, pomVersion, dependencies);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
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
                "    <dependency>\n" +
                "      <groupId>log4j</groupId>\n" +
                "      <artifactId>log4j</artifactId>\n" +
                "      <version>1.2.14</version>\n" +
                "    </dependency>\n" +
                "  </dependencies>\n" +
                "</project>";
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void pomWithTwoDepsWithinTypeClassifier() {
        List<POMDependency> dependencies = new ArrayList<>();
        dependencies.add(new POMDependency("junit", "junit", VersionFactory.get("4.8.1"), "sources", "jar"));
        dependencies.add(new POMDependency("log4j", "log4j", VersionFactory.get("1.2.14")));
        final POMArtifact pomArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, pomVersion, dependencies);
        String actualContent = pomArtifact.toXML();
        String expectedContent = "<project xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
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
        assertEquals(expectedContent, actualContent);
    }


    @Test
    public void serializeAndDeserialize() {
        List<POMDependency> inputDependencies = new ArrayList<>();
        inputDependencies.add(new POMDependency("junit", "junit", VersionFactory.get("4.8.1"), "sources", "jar"));
        inputDependencies.add(new POMDependency("log4j", "log4j", VersionFactory.get("1.2.14")));
        final POMArtifact inputPOMArtifact = new POMArtifact(POMParentFactory.noParent(), POM_GROUPID, POM_ARTIFACTID, pomVersion, inputDependencies);

        String pomContent = inputPOMArtifact.toXML();
        POMBuilder pomBuilder = new POMBuilder();
        POMArtifact pomArtifact1 = pomBuilder.buildFromXML(pomContent, new POMContext(POM_GROUPID, POM_ARTIFACTID, POM_VERSION));

        assertEquals(inputPOMArtifact.getGroupId(), pomArtifact1.getGroupId());
        assertEquals(inputPOMArtifact.getArtifactId(), pomArtifact1.getArtifactId());
        assertEquals(inputPOMArtifact.getVersion().getValue(), pomArtifact1.getVersion().getValue());
        List<POMDependency> resultDependencies = inputPOMArtifact.getDependencies();
        assertNotNull(resultDependencies);
        assertEquals(inputDependencies.size(), resultDependencies.size());

        for (int k = 0; k < resultDependencies.size(); k++) {
            POMDependency resultDependency = resultDependencies.get(k);
            POMDependency inputDependency = inputDependencies.get(k);
            assertEquals(inputDependency.getGroupId(), resultDependency.getGroupId());
            assertEquals(inputDependency.getArtifactId(), resultDependency.getArtifactId());
            assertEquals(inputDependency.getVersion().getValue(), resultDependency.getVersion().getValue());
        }
    }


}
