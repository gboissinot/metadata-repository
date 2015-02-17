package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
public class POMParentVersionFactory {

    public static POMParentVersion parentVersionWithWideRange() {
        return RangeLatestValuePOMParentVersion.getInstance();
    }

    public static POMParentVersion parentVersionWithLatestKeyword() {
        return ReleaseKeywordPOMParentVersion.getInstance();
    }

    public static POMParentVersion parentVersionWithGivenVersionValue(String version) {
        if (version == null) {
            throw new IllegalStateException("A parent version is required.");
        }
        return new SimplePOMParentVersion(version);
    }
}
