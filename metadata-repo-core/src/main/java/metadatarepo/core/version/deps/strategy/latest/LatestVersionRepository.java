package metadatarepo.core.version.deps.strategy.latest;

import metadatarepo.core.moduleId.ModuleId;

import java.util.Collection;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public interface LatestVersionRepository {

    String getLatestVersionValue(String org, String name, String status);

    Map<String, Collection<ModuleId>> getLatestDependencies();
}
