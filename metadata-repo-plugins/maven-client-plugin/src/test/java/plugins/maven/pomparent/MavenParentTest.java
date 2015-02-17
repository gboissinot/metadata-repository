package plugins.maven.pomparent;

import org.junit.Test;
import plugins.maven.pomparent.version.POMParentVersion;
import plugins.maven.pomparent.version.POMParentVersionFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author Gregory Boissinot
 */
public class MavenParentTest {

    private static final String TEST_PARENT_GROUPID = "testGroupId";
    private static final String TEST_PARENT_ARTIFACTID = "testGroupId";
    private static final String TEST_PARENT_VERSION = "1.0";

    @Test(expected = IllegalArgumentException.class)
    public void testNullParentGroupId() {
        new SimplePOMParent(null, TEST_PARENT_ARTIFACTID,
                POMParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION)) {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParentArtifactId() {
        new SimplePOMParent(TEST_PARENT_GROUPID, null, POMParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION)) {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParentVersion() {
        new SimplePOMParent(TEST_PARENT_GROUPID, TEST_PARENT_ARTIFACTID, null) {
        };
    }

    @Test(expected = IllegalStateException.class)
    public void testNoParentVersion() {
        new SimplePOMParent(TEST_PARENT_GROUPID, TEST_PARENT_ARTIFACTID, new POMParentVersion() {
            @Override
            public String getValue() {
                return null;
            }
        }) {
        };
    }

    @Test
    public void testValidParentCreation() {
        POMParent pomParent = new SimplePOMParent(TEST_PARENT_GROUPID, TEST_PARENT_ARTIFACTID,
                POMParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION));
        assertEquals(TEST_PARENT_GROUPID, pomParent.getGroupId());
        assertEquals(TEST_PARENT_ARTIFACTID, pomParent.getArtifactId());
        assertEquals(TEST_PARENT_VERSION, pomParent.getParentVersion().getValue());
    }
}
