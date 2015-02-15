package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
public class ParentVersionFactory {

    public static ParentVersion parentVersionWithWideRange() {
        return RangeLatestValueParentVersion.getInstance();
    }

    public static ParentVersion parentVersionWithLatestKeyword() {
        return ReleaseKeywordParentVersion.getInstance();
    }

    public static ParentVersion parentVersionWithGivenVersionValue(String version) {
        if (version == null) {
            throw new IllegalStateException("A parent version is required.");
        }
        return new SimpleParentVersion(version);
    }
}
