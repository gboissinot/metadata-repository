package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
class RangeLatestValueParentVersion extends AbstractParentVersion {

    private static final String WIDE_MVN_RANGE = "[1..3000]";

    private static RangeLatestValueParentVersion instance = new RangeLatestValueParentVersion();

    private RangeLatestValueParentVersion() {
        super(WIDE_MVN_RANGE);
    }

    public static RangeLatestValueParentVersion getInstance() {
        return instance;
    }
}
