package plugins.maven.pom;

/**
 * @author Gregory Boissinot
 */
public class ConversionExcetion extends RuntimeException {

    public ConversionExcetion(String message) {
        super(message);
    }

    public ConversionExcetion(Throwable cause) {
        super(cause);
    }
}
