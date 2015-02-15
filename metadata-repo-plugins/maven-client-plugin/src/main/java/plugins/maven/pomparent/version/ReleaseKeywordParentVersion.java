package plugins.maven.pomparent.version;


/**
 * @author Gregory Boissinot
 */
class ReleaseKeywordParentVersion extends AbstractParentVersion {

    private static final String RELEASE_MAVEN_KEYWORD = "RELEASE";

    private static ReleaseKeywordParentVersion instance = new ReleaseKeywordParentVersion();

    private ReleaseKeywordParentVersion() {
        super(RELEASE_MAVEN_KEYWORD);
    }

    public static ReleaseKeywordParentVersion getInstance() {
        return instance;
    }
}
