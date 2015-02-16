package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @author Gregory Boissinot
 */
public class POMBuilder {

    public POMArtifact buildFromXML(String mavenPOMContent, POMContext pomContext) {
        if (mavenPOMContent == null) {
            throw new IllegalArgumentException("You must provide a maven POM content in XML");
        }
        if (pomContext == null) {
            throw new IllegalArgumentException("You must provide additional information through a context for building a Maven POM object graph.");
        }

        POMArtifactStruct pomArtifactStruct = deserialize(mavenPOMContent);
        return pomArtifactStruct.fromXML(pomContext);
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
}
