package metadatarepo.core.version;

/**
 * @author Gregory Boissinot
 */
public class NoVersion implements Version {

    private static NoVersion instance = new NoVersion();

    private NoVersion() {
    }

    public static NoVersion getInstance() {
        return instance;
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }
}
