package metadatarepo.core.version;

/**
 * @author Gregory Boissinot
 */
class SimpleVersion implements Version {
    private final String value;

    public SimpleVersion(String value) {
        if (value == null) {
            throw new IllegalArgumentException("A version is required.");
        }
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleVersion version = (SimpleVersion) o;

        if (value != null ? !value.equals(version.value) : version.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
