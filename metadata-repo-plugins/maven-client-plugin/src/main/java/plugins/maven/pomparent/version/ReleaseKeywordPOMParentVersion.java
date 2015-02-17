package plugins.maven.pomparent.version;


/**
 * @author Gregory Boissinot
 */
class ReleaseKeywordPOMParentVersion extends AbstractPOMParentVersion {

    private static final String RELEASE_MAVEN_KEYWORD = "RELEASE";

    private static ReleaseKeywordPOMParentVersion instance = new ReleaseKeywordPOMParentVersion();

    private ReleaseKeywordPOMParentVersion() {
        super(RELEASE_MAVEN_KEYWORD);
    }

    public static ReleaseKeywordPOMParentVersion getInstance() {
        return instance;
    }
}
