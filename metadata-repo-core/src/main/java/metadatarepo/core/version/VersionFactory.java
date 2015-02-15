package metadatarepo.core.version;

/**
 * @author Gregory Boissinot
 */
public class VersionFactory {

    public static Version get(String value) {
        if (value == null) {
            return NoVersion.getInstance();
        } else {
            return new SimpleVersion(value);
        }
    }

    public static Version noVersion() {
        return NoVersion.getInstance();
    }
}
