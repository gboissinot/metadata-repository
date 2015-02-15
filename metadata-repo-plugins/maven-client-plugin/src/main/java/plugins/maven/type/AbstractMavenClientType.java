package plugins.maven.type;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.metadataexport.ModuleExportService;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.moduleId.ModuleMetaVersion;
import metadatarepo.core.moduleId.ModuleStatus;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pom.POMDependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
abstract class AbstractMavenClientType implements MavenClientType {

    private ModuleExportService moduleExportService;

    @Override
    public POMArtifact generateXMLPOM(ModuleId moduleId) {
        return new POMArtifact(
                getLatestDependencyBOM(),
                moduleId.getOrg(),
                moduleId.getName(),
                getPOMVerison(moduleId),
                getPOMMavenDependencies(
                        getDependencyVersionStrategy(),
                        moduleId)
        );
    }

    private Version getPOMVerison(ModuleId moduleId) {
        POMVersionEngine pomVersionEngine = new POMVersionEngine();
        return pomVersionEngine.buildMavenPOMVersion(moduleId);
    }

    private List<POMDependency> getPOMMavenDependencies(DependencyVersionStrategy versionStrategy, ModuleId moduleId) {
        List<POMDependency> mavenPOMDependencies = new ArrayList<POMDependency>();
        List<BuildDependency> exportedDependencies = moduleExportService.getBuildDependencies(
                versionStrategy, moduleId);
        for (BuildDependency exportedDependency : exportedDependencies) {
            mavenPOMDependencies.add(buildMavenPOMDependency(exportedDependency));
        }

        return mavenPOMDependencies;
    }

    private POMDependency buildMavenPOMDependency(BuildDependency exportedDependency) {
        POMDependency POMDependency = new POMDependency(
                exportedDependency.getOrg(),
                exportedDependency.getName(),
                exportedDependency.getVersion(),
                getStringValueIfAny("classifier", exportedDependency),
                getStringValueIfAny("type", exportedDependency));
        return POMDependency;
    }

    private String getStringValueIfAny(String extraMapKey, BuildDependency buildDependency) {
        Map<String, ?> extraAttributes = buildDependency.getExtraAttributes();
        Object classifierValue = extraAttributes.get(extraMapKey);
        if (classifierValue == null) {
            return null;
        }
        return String.class.cast(classifierValue);
    }


    private class POMVersionEngine {

        private String VERSION_STATUS_SEPERATOR = ".";

        public Version buildMavenPOMVersion(ModuleId moduleId) {
            ModuleMetaVersion metaVersion = moduleId.getMetaVersion();
            Version version = metaVersion.getVersion();
            if (metaVersion.isStatusDefined()) {
                return contactVersionWithStatus(version, metaVersion.getStatus());
            } else {
                return version;
            }
        }

        private Version contactVersionWithStatus(Version version, ModuleStatus status) {
            assert version != null;
            assert status != null;
            return VersionFactory.get(version.getValue() + VERSION_STATUS_SEPERATOR + status.getValue());
        }
    }
}
