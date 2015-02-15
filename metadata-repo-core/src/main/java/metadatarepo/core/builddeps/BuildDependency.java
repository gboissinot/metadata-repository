package metadatarepo.core.builddeps;


import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;

import java.util.Collections;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class BuildDependency {

    private static final Map<String, ? extends Object> NO_EXTRA_ATTRIBUTES = Collections.emptyMap();

    private String org;
    private String name;
    private Version version;
    private Map<String, ? extends Object> extraAttributes;

    public BuildDependency(String org, String name, String version, Map<String, ? extends Object> extraAttributes) {
        checkMandatoryParameters(org, name);
        set(org, name, VersionFactory.get(version), extraAttributes);
    }

    public BuildDependency(String org, String name, Version version, Map<String, ? extends Object> extraAttributes) {
        checkMandatoryParameters(org, name);
        set(org, name, version, extraAttributes);
    }

    private void checkMandatoryParameters(String org, String name) {
        if (org == null) {
            throw new IllegalArgumentException("An organization is required.");
        }
        if (name == null) {
            throw new IllegalArgumentException("A name is required.");
        }
    }

    private void set(String org, String name, Version version, Map<String, ? extends Object> extraAttributes) {
        this.org = org;
        this.name = name;
        this.version = version;
        this.extraAttributes = (extraAttributes == null) ? NO_EXTRA_ATTRIBUTES : extraAttributes;
    }

    public String getOrg() {
        return org;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public Map<String, ? extends Object> getExtraAttributes() {
        return Collections.unmodifiableMap(extraAttributes);
    }

    public boolean isExtraAttributes() {
        return !extraAttributes.isEmpty();
    }
}
