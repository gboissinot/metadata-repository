package plugins.maven;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.metadataexport.ModuleExportService;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.version.VersionFactory;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pom.POMDependency;
import plugins.maven.pomparent.BOMLatestDeps;
import plugins.maven.type.MavenClientType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class POMGenerationService {

    private ModuleExportService moduleExportService;

    public POMArtifact generatePOM(
            MavenClientType mavenClientType,
            MavenArtifactRequest mavenArtifactRequest) {
        return new POMArtifact(
                BOMLatestDeps.BOM_DEPS_GROUP_ID,
                BOMLatestDeps.BOM_DEPS_ARTIFACT_ID,
                mavenClientType.getLatestBOMVersion(),
                mavenArtifactRequest.getGroupId(),
                mavenArtifactRequest.getArtifactId(),
                VersionFactory.get(mavenArtifactRequest.getVersion()),
                getPOMMavenDependencies(
                        mavenClientType.getDependencyVersionStrategy(),
                        mavenArtifactRequest.getModuleId())
        );
    }

    private List<POMDependency> getPOMMavenDependencies(DependencyVersionStrategy versionStrategy, ModuleId moduleId) {
        List<POMDependency> mavenPOMDependencies = new ArrayList<POMDependency>();
        List<BuildDependency> exportedDependencies =
                moduleExportService.getBuildDependencies(versionStrategy, moduleId);
        for (BuildDependency exportedDependency : exportedDependencies) {
            mavenPOMDependencies.add(buildMavenPOMDependency(exportedDependency));
        }

        return mavenPOMDependencies;
    }

    private POMDependency buildMavenPOMDependency(BuildDependency exportedDependency) {
        POMDependency pomDependency = new POMDependency(
                exportedDependency.getOrg(),
                exportedDependency.getName(),
                exportedDependency.getVersion(),
                getStringValueIfAny("classifier", exportedDependency),
                getStringValueIfAny("type", exportedDependency));
        return pomDependency;
    }

    private String getStringValueIfAny(String extraMapKey, BuildDependency buildDependency) {
        Map<String, ?> extraAttributes = buildDependency.getExtraAttributes();
        Object classifierValue = extraAttributes.get(extraMapKey);
        if (classifierValue == null) {
            return null;
        }
        return String.class.cast(classifierValue);
    }
}
