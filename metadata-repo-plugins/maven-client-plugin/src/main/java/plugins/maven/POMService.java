package plugins.maven;

import metadatarepo.core.builddeps.BuildDependency;
import metadatarepo.core.metadataexport.ModuleExportService;
import metadatarepo.core.moduleId.ModuleId;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.deps.strategy.DependencyVersionStrategy;
import plugins.maven.pom.POM;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pom.POMDependency;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.pomparent.version.ParentVersionFactory;
import plugins.maven.type.MavenClientType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class POMService {

    private ModuleExportService moduleExportService;

    public POMService(ModuleExportService moduleExportService) {
        this.moduleExportService = moduleExportService;
    }

    public String generatePOMContentForModuleId(MavenClientType mavenClientType,
                                                ModuleId moduleId) {
        POMArtifact pomArtifact = getArtifactPOM(mavenClientType, moduleId);
        return pomArtifact.toXML();
    }

    public String generateBOMDeps(String givenVersion) {
        POM bomDeps = POMParentFactory.bomDeps(ParentVersionFactory.parentVersionWithGivenVersionValue(givenVersion));
        return bomDeps.toXML();
    }

    public String generateBOMBuild(String givenVersion){
        POM bomBuild = POMParentFactory.bomBuild(ParentVersionFactory.parentVersionWithGivenVersionValue(givenVersion));
        return bomBuild.toXML();
    }


//    public POM getModuleId(String pomContent) {
//        //TODO
//        return null;
//    }
//
//    public Collection<BuildDependency> getBuildDependencies(String pomContent) {
//        //TODO
//        return null;
//
//    }

    private POMArtifact getArtifactPOM(MavenClientType mavenClientType,
                                       ModuleId moduleId) {

        //TODO cas gradle à prendre en compte sans BOM (sha1 et md5)
        return new POMArtifact(
                getPOMParent(mavenClientType),
                moduleId.getOrg(),
                moduleId.getName(),
                getPOMVerison(moduleId),
                getPOMMavenDependencies(
                        mavenClientType.getDependencyVersionStrategy(),
                        moduleId));
    }

    private Version getPOMVerison(ModuleId moduleId) {
        POMVersionEngine pomVersionEngine = new POMVersionEngine();
        return pomVersionEngine.buildMavenPOMVersion(moduleId);
    }

    private POMParent getPOMParent(MavenClientType mavenClientType) {
        return mavenClientType.getLatestDependencyBOM();
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
}
