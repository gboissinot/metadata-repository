package util.mongodb.exception;

/**
 * @author Gregory Boissinot
 */
public class NoSuchDocumentException extends MongoDBException {

    public NoSuchDocumentException() {
    }

    public NoSuchDocumentException(String message) {
        super(message);
    }

    public NoSuchDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchDocumentException(Throwable cause) {
        super(cause);
    }

}
