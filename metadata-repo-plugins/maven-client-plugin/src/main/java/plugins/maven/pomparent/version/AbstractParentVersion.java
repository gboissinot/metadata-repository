package plugins.maven.pomparent.version;

/**
 * @author Gregory Boissinot
 */
public abstract class AbstractParentVersion implements ParentVersion {

    private String value;

    protected AbstractParentVersion(String parentVersionValue) {
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
