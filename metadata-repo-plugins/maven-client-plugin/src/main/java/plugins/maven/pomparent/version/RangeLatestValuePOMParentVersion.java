package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
class RangeLatestValuePOMParentVersion extends AbstractPOMParentVersion {

    private static final String WIDE_MVN_RANGE = "[1..3000]";

    private static RangeLatestValuePOMParentVersion instance = new RangeLatestValuePOMParentVersion();

    private RangeLatestValuePOMParentVersion() {
        super(WIDE_MVN_RANGE);
    }

    public static RangeLatestValuePOMParentVersion getInstance() {
        return instance;
    }
}
