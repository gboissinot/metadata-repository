package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import metadatarepo.core.version.Version;
import metadatarepo.core.version.VersionFactory;
import plugins.maven.MavenArtifactRequest;
import plugins.maven.pomparent.NoPOMParent;
import plugins.maven.pomparent.POMParent;
import plugins.maven.pomparent.POMParentFactory;
import plugins.maven.type.MavenClientType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class POMBuilderService {

    public POMArtifact buildFromXML(
            MavenClientType mavenClientType,
            MavenArtifactRequest mavenArtifactRequest,
            String mavenPOMContent) {

        POMArtifactStruct pomArtifactStruct = deserialize(mavenPOMContent);
        String groupId = extractGroupId(mavenArtifactRequest, pomArtifactStruct);
        String artifactId = extractArtifactId(pomArtifactStruct);
        Version pomVersion = extractMavenPOMVersion(mavenArtifactRequest, pomArtifactStruct);
        List<POMDependency> dependencies = extractDependencies(pomArtifactStruct);
        final POMParent pomParent = extractParent(mavenClientType, pomArtifactStruct);
        if (pomParent instanceof NoPOMParent) {
            return new POMArtifact(groupId, artifactId, pomVersion, dependencies);
        } else {
            return new POMArtifact(pomParent.getGroupId(), pomParent.getArtifactId(), pomParent.getParentVersion(),
                    groupId, artifactId, pomVersion, dependencies);
        }
    }

    private POMParent extractParent(MavenClientType mavenClientType, POMArtifactStruct pomArtifactStruct) {
        final POMArtifactStruct.MavenPOMParentStuct parent = pomArtifactStruct.parent;
        if (parent == null) {
            return POMParentFactory.noParent();
        } else {
            return POMParentFactory.regularParent(parent.groupId, parent.artifactId, mavenClientType.fixParentVersion(parent.version));
        }
    }

    private POMArtifactStruct deserialize(String mavenPOMContent) {
        XStream xstream = new XStream(new DomDriver("UTF-8")) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        xstream.alias("project", POMArtifactStruct.class);
        xstream.alias("dependency", POMArtifactStruct.MavenPOMDependencyStruct.class);
        xstream.alias("profile", POMArtifactStruct.MavenPOMProfile.class);
        try {
            return (POMArtifactStruct) xstream.fromXML(mavenPOMContent);
        } catch (Throwable e) {
            throw new ConversionExcetion(e);
        }
    }

    private String extractGroupId(MavenArtifactRequest mavenArtifactRequest, POMArtifactStruct pomArtifactStruct) {
        if (pomArtifactStruct.groupId == null) {
            return mavenArtifactRequest.getGroupId();
        }
        return pomArtifactStruct.groupId;
    }

    private String extractArtifactId(POMArtifactStruct pomArtifactStruct) {
        validateNotNull("artifactId", pomArtifactStruct.artifactId);
        return pomArtifactStruct.artifactId;
    }

    private List<POMDependency> extractDependencies(POMArtifactStruct pomArtifactStruct) {
        if (pomArtifactStruct.dependencies == null) {
            return Collections.EMPTY_LIST;
        }
        List<POMDependency> dependenciesResult = new ArrayList<>();
        for (POMArtifactStruct.MavenPOMDependencyStruct structDependency : pomArtifactStruct.dependencies) {
            String structDepVersion = structDependency.version;
            Version versionDependency;
            if (structDepVersion == null) {
                versionDependency = VersionFactory.noVersion();
            } else {
                versionDependency = VersionFactory.get(structDepVersion);
            }
            POMDependency POMDependency = new POMDependency(
                    structDependency.groupId,
                    structDependency.artifactId,
                    versionDependency,
                    structDependency.classifier,
                    structDependency.type);
            dependenciesResult.add(POMDependency);
        }
        return dependenciesResult;
    }

    private Version extractMavenPOMVersion(MavenArtifactRequest mavenArtifactRequest, POMArtifactStruct pomArtifactStruct) {
        if (pomArtifactStruct.version == null) {
            return VersionFactory.get(mavenArtifactRequest.getVersion());
        }
        return VersionFactory.get(pomArtifactStruct.version);
    }

    private void validateNotNull(String filedName, String fieldValue) {
        if (fieldValue == null) {
            throw new ConversionExcetion(String.format("%s must have a value.", filedName));
        }
    }

}
