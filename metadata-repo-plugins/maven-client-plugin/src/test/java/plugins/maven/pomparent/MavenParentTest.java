package plugins.maven.pomparent;

import org.junit.Test;
import plugins.maven.pomparent.version.ParentVersion;
import plugins.maven.pomparent.version.ParentVersionFactory;

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
                ParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION)) {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParentArtifactId() {
        new SimplePOMParent(TEST_PARENT_GROUPID, null, ParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION)) {
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParentVersion() {
        new SimplePOMParent(TEST_PARENT_GROUPID, TEST_PARENT_ARTIFACTID, null) {
        };
    }

    @Test(expected = IllegalStateException.class)
    public void testNoParentVersion() {
        new SimplePOMParent(TEST_PARENT_GROUPID, TEST_PARENT_ARTIFACTID, new ParentVersion() {
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
                ParentVersionFactory.parentVersionWithGivenVersionValue(TEST_PARENT_VERSION));
        assertEquals(TEST_PARENT_GROUPID, pomParent.getGroupId());
        assertEquals(TEST_PARENT_ARTIFACTID, pomParent.getArtifactId());
        assertEquals(TEST_PARENT_VERSION, pomParent.getParentVersion().getValue());
    }
}
