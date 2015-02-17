package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
public abstract class AbstractPOMParentVersion implements POMParentVersion {

    private String value;

    protected AbstractPOMParentVersion(String parentVersionValue) {
        if (parentVersionValue == null) {
            throw new IllegalStateException("A parent must have a version.");
        }
        this.value = parentVersionValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
