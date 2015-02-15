package plugins.maven.pom;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import plugins.maven.pom.POMArtifact;
import plugins.maven.pom.POMContext;
import plugins.maven.pom.ConversionExcetion;

import static plugins.maven.pom.POMArtifact.POMArtifactStruct;

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
        return pomArtifactStruct.build(pomContext);
    }

    private POMArtifactStruct deserialize(String mavenPOMContent) {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.alias("project", POMArtifactStruct.class);
        xstream.alias("dependency", POMArtifactStruct.MavenPOMDependencyStruct.class);
        try {
            return (POMArtifactStruct) xstream.fromXML(mavenPOMContent);
        } catch (Throwable e) {
            throw new ConversionExcetion(e);
        }
    }
}
