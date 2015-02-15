package plugins.maven.type;

/**
 * @author Gregory Boissinot
 */
public class MavenClientFactory {

    public static MavenClientType get(String userAgent) {
        if (isMvn3(userAgent)) {
            return Mvn3ClientType.getInstance();
        } else if (isGradle(userAgent)) {
            return GradleClientType.getInstance();
        } else if (isMvn2(userAgent)) {
            return Mvn2ClientType.getInstance();
        } else {
            return Mvn3ClientType.getInstance();
        }
    }

    private static boolean isGradle(String userAgent) {
        //TODO
        return false;
    }

    private static boolean isMvn3(String userAgent) {
        //TODO
        return false;
    }

    private static boolean isMvn2(String userAgent) {
        //TODO
        return false;
    }

}
